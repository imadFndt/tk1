import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.*

typealias Matrix = D2Array<Int>
typealias Row = MultiArray<Int, D1>

const val NO_POSITION = -1

val Matrix.rows get() = this.shape.component1()
val Matrix.columns get() = this.shape.component2()

fun Matrix.column(column: Int) = this[0.r..this.rows, column].toList()

fun Matrix.ref(): Matrix {
    val result = deepCopy()
    var row = 0
    (0 until columns).forEach { i ->
        val index = result.findFirstOneUnder(column = i, row)
        when {
            index == row -> {
                result.destroyOnes(i, index)
                row++
            }
            index == -1 -> Unit
            index > row -> {
                result.swapRows(row, index)
                result.destroyOnes(i, row)
                row++
            }
        }
    }
    return result
}

operator fun Row.plus(array: Row) = this.mapIndexed { index: Int, item: Int ->
    item xor array[index]
}

fun Matrix.swapRows(destinationIndex: Int, sourceIndex: Int) = let { result ->
    val sourceRow = result[sourceIndex].deepCopy()
    result[sourceIndex] = result[destinationIndex]
    result[destinationIndex] = sourceRow
}

fun Matrix.findFirstOneUnder(column: Int, row: Int) =
    column(column).mapIndexed { index, item ->
        if (index >= row && item == 1) index else null
    }.filterNotNull()
        .firstOrNull() ?: NO_POSITION

fun Matrix.destroyOnes(i: Int, index: Int) {
    lowerOnesInColumn(i, index).forEach { rowIndex ->
        val a = this[rowIndex] + this[index]
        this[rowIndex] = a
    }
}

fun Matrix.lowerOnesInColumn(column: Int, underRowIndex: Int = 0) = onesInColumn(column).filter { it > underRowIndex }

fun Matrix.onesInColumn(column: Int) = column(column).toList()
    .mapIndexed { index, item ->
        when (item) {
            1 -> index
            else -> null
        }
    }
    .filterNotNull()


fun Matrix.rref(): Matrix {
    val result = this.ref().filterZerosRows().deepCopy()
    println(result)
    (1 until result.rows).forEach { i ->
        val row = result[i]
        val pivotIndex = row.findPivotIndex()
        result.destroyUpperOnes(pivotIndex, i)
    }
    return result
}

fun Matrix.filterZerosRows(): Matrix {
    val result = mutableListOf<List<Int>>()
    (0 until rows).forEach { i ->
        val row = this[i]
        if (!row.all { it == 0 }) {
            result.add(row.toList())
        }
    }
    return mk.ndarray(result)
}

fun Row.findPivotIndex() = this.toList().indexOfFirst { it == 1 }

fun Matrix.destroyUpperOnes(i: Int, index: Int) {
    upperOnesInColumn(i, index).forEach { rowIndex ->
        val a = this[rowIndex] + this[index]
        this[rowIndex] = a
    }
}

fun Matrix.upperOnesInColumn(column: Int, upperRowIndex: Int) = onesInColumn(column).filter { it < upperRowIndex }


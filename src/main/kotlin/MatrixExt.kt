import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.*

typealias Matrix = D2Array<Int>
typealias Row = MultiArray<Int, D1>

const val NO_POSITION = -1

val Matrix.rows get() = this.shape.component1()
val Matrix.columns get() = this.shape.component2()

fun Matrix.column(column: Int) =  this[0.r..this.rows, column].toList()

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

fun Matrix.columnFrom(column: Int, row: Int) = this[row.r..this.rows, column]

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
    onesInColumn(i, index).forEach { rowIndex ->
        val a = this[rowIndex] + this[index]
        this[rowIndex] = a
    }
}

fun Matrix.onesInColumn(column: Int, underRowIndex: Int = 0) = column(column).toList()
    .mapIndexed { index, item ->
        when (item) {
            1 -> index
            else -> null
        }
    }
    .filterNotNull()
    .filter { it > underRowIndex }

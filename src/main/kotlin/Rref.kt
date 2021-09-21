import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import utils.*

fun Matrix.rref(): Matrix {
    val result = this.ref().filterZerosRows().deepCopy()
    (1 until result.rows).forEach { i ->
        val row = result[i]
        val pivotIndex = row.findPivotIndex()
        result.destroyUpperOnes(pivotIndex, i)
    }
    return result
}

fun Matrix.filterZerosRows() = this.to2DList().filter { row -> !row.all { it == 0 } }.toMatrix()

fun Row.findPivotIndex() = this.toList().indexOfFirst { it == 1 }

fun Matrix.destroyUpperOnes(i: Int, index: Int) {
    upperOnesInColumn(i, index).forEach { rowIndex ->
        this[rowIndex] = this[rowIndex] + this[index]
    }
}

fun Matrix.upperOnesInColumn(columnIndex: Int, upperRowIndex: Int) = column(columnIndex).toList()
    .mapIndexedNotNull { index, item ->
        when (item) {
            1 -> index
            else -> null
        }
    }.filter { it < upperRowIndex }
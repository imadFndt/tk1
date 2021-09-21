import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import utils.*

fun Matrix.rref(): Matrix {
    val result = ref().filterZerosRows().deepCopy()
    (1 until result.rows).forEach { i ->
        val row = result[i]
        val pivotIndex = row.findPivotIndex()
        result.destroyOnesAbove(pivotIndex, i)
    }
    return result
}

fun Matrix.filterZerosRows() = to2DList().filter { row -> !row.all { it == 0 } }.toMatrix()

fun Row.findPivotIndex() = toList().indexOfFirst { it == 1 }

fun Matrix.destroyOnesAbove(i: Int, index: Int) {
    onesInColumn(i, aboveRowIndex = index).forEach { rowIndex ->
        this[rowIndex] = this[rowIndex] + this[index]
    }
}
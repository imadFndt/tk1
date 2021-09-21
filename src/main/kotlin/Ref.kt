import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.r
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.indexOfFirst
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import utils.*

fun Matrix.ref(): Matrix {
    val result = deepCopy()

    var row = 0

    (0 until columns).forEach { i ->

        val index = result.findColumnIndexUnderPosition(column = i, row)

        if (index >= row) {

            if (index != row) result.swapRows(row, index)
            result.destroyOnes(i, row)
            row++
        }
    }
    return result
}

fun Matrix.findColumnIndexUnderPosition(column: Int, row: Int): Int {

    if (row == rows) return -1

    return when (val firstInColumn = this[row.r..rows, column].indexOfFirst { it == 1 }) {

        -1 -> firstInColumn
        else -> firstInColumn + row
    }
}

fun Matrix.destroyOnes(i: Int, index: Int) {

    onesInColumn(
        column = i,
        underRowIndex = index
    ).forEach { rowIndex ->

        this[rowIndex] = this[rowIndex] + this[index]
    }
}

fun Matrix.onesInColumn(
    column: Int,
    underRowIndex: Int = 0,
    aboveRowIndex: Int = rows
) = this[0.r..rows, column].toList()
    .mapIndexedNotNull { index, item ->

        when (item) {
            1 -> index
            else -> null
        }
    }
    .filter { it in (underRowIndex + 1) until aboveRowIndex }

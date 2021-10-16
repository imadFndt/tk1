package matrix.utils

import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.r
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

fun Matrix.onesInColumn(
    column: Int,
    underRowIndex: Int = -1,
    aboveRowIndex: Int = rows
) = this[0.r..rows, column].toList()
    .mapIndexedNotNull { index, item ->

        when (item) {
            1 -> index
            else -> null
        }
    }
    .filter { it in (underRowIndex + 1) until aboveRowIndex }
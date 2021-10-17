package matrix

import matrix.utils.Matrix
import matrix.utils.to2DList

fun distance(matrix: Matrix) = firstMethod(matrix)
    .to2DList()
    .map { it.sum() }
    .minOrNull() ?: -1
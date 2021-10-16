import matrix.utils.Matrix
import matrix.utils.to2DList

fun distance(matrix: Matrix) = matrix.to2DList()
    .map { row -> row.reduce { acc, i -> acc + i } }
    .minOrNull() ?: -1
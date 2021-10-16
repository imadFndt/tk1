package lab2.errors

import matrix.utils.Matrix
import matrix.utils.to2DList
import wordsForMultiplicity

object OneSizedErrorSolver : ErrorSolver {

    override fun errorsMatrix(length: Int): Matrix = wordsForMultiplicity(
        multiplicity = 1,
        length = length
    )

    override fun findErrorBySyndrom(checkSyndrom: List<Int>, checkingMatrix: Matrix): List<Int> {

        return when (val syndromIndex = checkingMatrix
            .to2DList()
            .indexOf(checkSyndrom)
        ) {

            -1 -> emptyList()
            else -> listOf(syndromIndex)
        }
    }
}
package lab2.errors

import matrix.utils.Matrix
import matrix.utils.to2DList
import matrix.utils.xorPlus
import matrix.wordsForMultiplicity


object TwoSizedErrorSolver : ErrorSolver {
    override fun errorsMatrix(length: Int): Matrix = wordsForMultiplicity(
        multiplicity = 2,
        length = length
    )

    override fun findErrorBySyndrom(checkSyndrom: List<Int>, checkingMatrix: Matrix): List<Int> {

        val checkingList = checkingMatrix.to2DList()

        return checkingList
            .mapIndexed { i, row ->

                when (val index = checkingList.indexOfFirst { (it xorPlus row) == checkSyndrom }) {

                    -1 -> null
                    else -> i to index
                }
            }
            .filterNotNull()
            .shuffled()
            .first()
            .toList()
    }
}
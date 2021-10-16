package lab2.errors

import matrix.utils.Matrix
import matrix.utils.to2DList
import matrix.utils.xorPlus
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import wordsForMultiplicity


object TwoSizedErrorSolver : ErrorSolver {
    override fun errorsMatrix(length: Int): Matrix = wordsForMultiplicity(
        multiplicity = 2,
        length = length
    )

    override fun findErrorBySyndrom(checkSyndrom: List<Int>, checkingMatrix: NDArray<Int, D2>): List<Int> {

        val checkingList = checkingMatrix.to2DList()

        return checkingList
            .mapNotNull { row ->

                when (val index = checkingList.indexOfFirst { (it xorPlus row) == checkSyndrom }) {

                    -1 -> null
                    else -> index
                }
            }
            .mapIndexed { index, value -> listOf(index, value) }
            .shuffled()
            .first()
            .toList()
    }
}
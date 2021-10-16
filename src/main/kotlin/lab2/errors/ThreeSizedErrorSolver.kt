package lab2.errors

import matrix.utils.Matrix
import matrix.utils.to2DList
import wordsForMultiplicity

object ThreeSizedErrorSolver : ErrorSolver {

    override fun errorsMatrix(length: Int): Matrix = wordsForMultiplicity(
        multiplicity = 3,
        length = length
    )

    override fun findErrorBySyndrom(checkSyndrom: List<Int>, checkingMatrix: Matrix): List<Int> {

        val checkingList = checkingMatrix.to2DList()

        val resultList = mutableListOf<List<Int>>()

        for (i in checkingList.indices) {

            val first = checkingList[i].toList()
            for (j in checkingList.indices) {

                val second = checkingList[j].toList()
                for (k in checkingList.indices) {
                    val third = checkingList[k].toList()

                    if ((first + second + third) == checkSyndrom) resultList.add(listOf(i, j, k))
                }
            }
        }

        return resultList.shuffled()
            .firstOrNull()
            ?: emptyList()
    }
}
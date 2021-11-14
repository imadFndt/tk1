package lab4.errors.golay

import lab2.errors.ErrorSolver
import lab4.createExtendedGolayCode
import matrix.multiply
import matrix.newWordsForMultiplicity
import matrix.utils.*
import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

class GolayErrorSolver(private val multiplicity: Int = 1) : ErrorSolver {

    private val golayCode by lazy(::createExtendedGolayCode)

    override fun errorsMatrix(length: Int): Matrix = newWordsForMultiplicity(multiplicity, 24).toMatrix()

    override fun findErrorBySyndrom(checkSyndrom: List<Int>, checkingMatrix: NDArray<Int, D2>): List<Int> {

        if (checkSyndrom.wt() <= 3) return checkSyndrom + List(12) { 0 }

        cond2(checkSyndrom, golayCode)?.let { return it }

        val secondSyndrom = checkSyndrom.toNDArray().multiply(golayCode).toList()

        if (secondSyndrom.wt() <= 3) return List(12) { 0 } + secondSyndrom

        cond2(secondSyndrom, golayCode)?.let { result ->

            return result.chunked(2)
                .reversed()
                .flatten()
        }

        return emptyList()
    }

    override fun findError(erroredWord: Row, checkingMatrix: Matrix): List<Int> {
        val checkSyndrom = erroredWord.multiply(checkingMatrix)
            .out("Синдром для ашыбки")
            .toList()

        return findErrorBySyndrom(checkSyndrom, checkingMatrix)
    }

    private fun cond2(checkSyndrom: List<Int>, checkingMatrix: NDArray<Int, D2>): List<Int>? {
        return checkingMatrix.to2DList().mapIndexedNotNull { index, bI ->

            val huetah = checkSyndrom xorPlus bI

            when (huetah.wt()) {
                in 0..2 -> huetah + List(12) { if (it == index) 1 else 0 }
                else -> null
            }
        }.firstOrNull()
    }
}

private fun List<Int>.wt() = this.sum()
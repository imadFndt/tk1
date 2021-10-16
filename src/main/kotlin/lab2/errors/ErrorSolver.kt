package lab2.errors

import matrix.utils.Matrix
import matrix.utils.Row
import matrix.utils.out
import multiply
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

interface ErrorSolver {

    fun errorsMatrix(length: Int): Matrix

    fun findError(erroredWord: Row, checkingMatrix: Matrix): List<Int> {

        val checkSyndrom = erroredWord.multiply(checkingMatrix)
            .out("Синдром для ашыбки")
            .toList()

        val syndromIndex = findErrorBySyndrom(checkSyndrom, checkingMatrix)

        println(
            when {

                syndromIndex.isEmpty() -> "Ашыбка не нашлась"
                else -> "Номер в таблице: $syndromIndex"
            }
        )

        return syndromIndex
    }

    fun findErrorBySyndrom(checkSyndrom: List<Int>, checkingMatrix: NDArray<Int, D2>): List<Int>
}
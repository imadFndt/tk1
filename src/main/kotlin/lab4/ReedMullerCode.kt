package lab4

import matrix.utils.*
import org.jetbrains.kotlinx.multik.api.empty
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import java.lang.IllegalArgumentException
import kotlin.math.pow

class ReedMullerCode(r: Int, m: Int) {
    val generatorMatrix: Matrix

    init {
        require(r <= m)

        fun createGeneratorMatrixOn(r: Int, m: Int): Matrix {

            return when (r) {

                0 -> listOf(List(2 shl m - 1) { 1 }).toMatrix()
                m -> {

                    val previousStep = createGeneratorMatrixOn(m - 1, m)
                    val bottomOfMatrix = listOf(List(previousStep.rows) { 0 } + 1)
                    (previousStep.to2DList().plus(bottomOfMatrix)).toMatrix()
                }
                in 1 until m -> {

                    val previousMStep = createGeneratorMatrixOn(r, m - 1).to2DList()
                    var rows: Int
                    var columns: Int
                    val previousRStep = createGeneratorMatrixOn(r - 1, m - 1).apply {
                        rows = this.rows
                        columns = this.columns
                    }.to2DList()
                    val topSide = previousMStep.mapIndexed { index, row ->
                        row + previousMStep[index]
                    }
                    val empty = mk.empty<Int, D2>(rows, columns).to2DList()

                    val bottomSide = empty.mapIndexed { index, row ->
                        row + previousRStep[index]
                    }
                    return (topSide + bottomSide).toMatrix()
                }
                else -> throw IllegalArgumentException()
            }
        }

        generatorMatrix = createGeneratorMatrixOn(r, m)
    }
}
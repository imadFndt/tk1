package lab3

import matrix.allWordsForLength
import matrix.distance
import matrix.utils.Matrix
import matrix.utils.out
import matrix.utils.to2DList
import matrix.utils.toMatrix
import org.jetbrains.kotlinx.multik.api.identity
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import kotlin.math.pow

fun main() {

    val r = 5
    println("r = $r")

    val checkingMatrix = createHammingH(r)
        .out("Проверочная матрица")

    val generaingMatrix = createGeneratingMatrix(checkingMatrix, r)
        .out("Порождающая матрица")
        .also {
            println(distance(it))
        }
}

fun createGeneratingMatrix(checkingMatrix: Matrix, r: Int): Matrix {
    return mk.identity<Int>(2.0.pow(r).toInt() - r - 1)
        .to2DList()
        .mapIndexed { i, row -> row + checkingMatrix[i].toList() }
        .toMatrix()
}

fun createHammingH(r: Int): Matrix {
    val allWords = allWordsForLength(r).filter { it.sum() > 1 }
        .reversed()

    val identity = mk.identity<Int>(r).to2DList()
    return (allWords + identity).toMatrix()
}
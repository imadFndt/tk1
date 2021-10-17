package lab3

import lab2.checkError
import lab2.errors.OneSizedErrorSolver
import lab2.errors.ThreeSizedErrorSolver
import lab2.errors.TwoSizedErrorSolver
import matrix.allWordsForLength
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

    val r = 4
    println("r = $r")

    val checkingMatrix = createHammingH(r)
        .out("Проверочная матрица")

    val generatingMatrix = createGeneratingMatrix(checkingMatrix, r)
        .out("Порождающая матрица")

    println("\n\nИсследование ошибки длиной один")
    checkError(
        generatingSet = generatingMatrix,
        checkingMatrix = checkingMatrix,
        errorSolver = OneSizedErrorSolver
    )

    println("\n\nИсследование ошибки длиной два")
    checkError(
        generatingSet = generatingMatrix,
        checkingMatrix = checkingMatrix,
        errorSolver = TwoSizedErrorSolver
    )

    println()
    println("\n\nРassширенный код Хемминга")
    val expandedHammingH = createExpandedHammingH(r)
        .out("Порождающая матрица")

    val generatingMatrixExpanded = createGeneratingMatrix(expandedHammingH, r - 1)
        .out("Порождающая матрица")

    println("\n\nИсследование ошибки длиной один")
    checkError(
        generatingSet = generatingMatrixExpanded,
        checkingMatrix = expandedHammingH,
        errorSolver = OneSizedErrorSolver
    )

    println("\n\nИсследование ошибки длиной два")
    checkError(
        generatingSet = generatingMatrixExpanded,
        checkingMatrix = expandedHammingH,
        errorSolver = TwoSizedErrorSolver
    )
    println("\n\nИсследование ошибки длиной три")
    checkError(
        generatingSet = generatingMatrixExpanded,
        checkingMatrix = expandedHammingH,
        errorSolver = ThreeSizedErrorSolver
    )
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

fun createExpandedHammingH(r: Int): Matrix {
    return createHammingH(r - 1).to2DList().toMutableList()
        .apply { add(List(first().size) { 0 }) }
        .map { it + 1 }
        .toList()
        .toMatrix()
}
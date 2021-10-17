package lab2

import lab2.errors.ErrorSolver
import lab2.errors.OneSizedErrorSolver
import lab2.errors.ThreeSizedErrorSolver
import lab2.errors.TwoSizedErrorSolver
import lab2.utils.generateSimpleSet
import matrix.LinearCode
import matrix.distance
import matrix.utils.*
import matrix.multiply
import org.jetbrains.kotlinx.multik.api.identity
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

fun main() {

    val generatingSet = TestMatrix.matrixTask2
        .out("G")

    println("columns: ${generatingSet.columns}")
    println("rows: ${generatingSet.rows}")
    println("distance: ${distance(generatingSet)}")

    println("Для ошибки размерности 1")
    findErrors(OneSizedErrorSolver, generatingSet)

    println()
    println()
    println("Для ошибки размерности 2")
    findErrors(TwoSizedErrorSolver, generatingSet)

    if (distance(generatingSet) - 1 < 3) return

    println()
    println()
    println("Для ошибки размерности 3")
    findErrors(ThreeSizedErrorSolver, generatingSet)
}

private fun findErrors(errorSolver: ErrorSolver, generatingSet: Matrix) {

    val linearCode = LinearCode(generatingSet)
    val checkingMatrix = linearCode.result
        .out("H")

    checkError(
        generatingSet = generatingSet,
        checkingMatrix = checkingMatrix,
        errorSolver = errorSolver,
    )
}

private fun checkError(generatingSet: Matrix, checkingMatrix: Matrix, errorSolver: ErrorSolver) {

    val randomWord = generateRandomWord(generatingSet.rows, generatingSet)
    val erroredWord = generateErrorInWord(randomWord, errorSolver.errorsMatrix(generatingSet.columns))
    val syndromIndexes = errorSolver.findError(erroredWord, checkingMatrix).toList()

    val error = List(generatingSet.columns) { if (it in syndromIndexes) 1 else 0 }.toNDArray()
        .out("Найденная ошибка")

    val fixed = (erroredWord + error).out("Исправленная ошибка")
    println("Исправленная совпадает с изначальным: ${fixed.toList() == randomWord.toList()}")
}

private fun generateRandomWord(rows: Int, generatingSet: Matrix): Row {

    val identity = mk.identity<Int>(rows)
    val chosenIdentity = identity[(0 until rows).random()]
        .out("Выбранная строчка из единичной матрицы")

    return chosenIdentity.multiply(generatingSet)
        .out("Полученное слово")
}

private fun generateErrorInWord(randomWord: Row, errorProvider: Matrix): Row {

    val randomError = errorProvider[(0 until randomWord.size).random()]
        .out("Ашыбка")
    return (randomWord + randomError)
        .out("Слово с ошибкой")
}

object TestMatrix {
    val matrixTask1 get() = generateSimpleSet(columns = 7, rows = 4, d = 3)

    val matrixTask2: Matrix = listOf(
        listOf(1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1),
        listOf(0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0),
        listOf(0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0),
        listOf(0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0),
    ).toMatrix()
}


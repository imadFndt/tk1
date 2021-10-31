package lab4

import lab2.checkError
import lab2.errors.FourSizedErrorSolver
import lab2.errors.OneSizedErrorSolver
import lab2.errors.ThreeSizedErrorSolver
import lab2.errors.TwoSizedErrorSolver
import matrix.distance
import matrix.utils.*
import org.jetbrains.kotlinx.multik.api.identity
import org.jetbrains.kotlinx.multik.api.mk

fun main() {

    val extendedGolayCode = createExtendedGolayCode()
        .out("Расширенный код Голея")

    val checkingMatrixGolay = createCheckingMatrixGolay(extendedGolayCode)
        .out("Проверочная матрица")

    val generatingMatrixGolay = createGeneratingMatrixGolay(extendedGolayCode)
        .out("Порождающая матрица")

//    println("\n\nИсследование ошибки длиной один")
//    checkError(
//        generatingSet = generatingMatrixGolay,
//        checkingMatrix = checkingMatrixGolay,
//        errorSolver = OneSizedErrorSolver
//    )
//
//    println("\n\nИсследование ошибки длиной два")
//    checkError(
//        generatingSet = generatingMatrixGolay,
//        checkingMatrix = checkingMatrixGolay,
//        errorSolver = TwoSizedErrorSolver
//    )
//
//    println("\n\nИсследование ошибки длиной три")
//    checkError(
//        generatingSet = generatingMatrixGolay,
//        checkingMatrix = checkingMatrixGolay,
//        errorSolver = ThreeSizedErrorSolver
//    )

    println("\n\nИсследование ошибки длиной четыре")
    checkError(
        generatingSet = generatingMatrixGolay,
        checkingMatrix = checkingMatrixGolay,
        errorSolver = FourSizedErrorSolver
    )

}

fun createExtendedGolayCode(): Matrix = listOf(
    listOf(1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1),
    listOf(1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1),
    listOf(0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1),
    listOf(1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1),
    listOf(1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1),
    listOf(1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1),
    listOf(0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1),
    listOf(0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1),
    listOf(0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1),
    listOf(1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1),
    listOf(0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1),
    listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0),
).toMatrix()

fun createGeneratingMatrixGolay(b: Matrix): Matrix {
    val identity = mk.identity<Int>(b.rows).to2DList()
    return b.to2DList()
        .mapIndexed { i, row ->
            identity[i] + row
        }
        .toMatrix()
}

fun createCheckingMatrixGolay(b: Matrix): Matrix {
    val identity = mk.identity<Int>(b.rows).to2DList()
    return (identity + b.to2DList()).toMatrix()
}
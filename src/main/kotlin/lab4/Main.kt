package lab4

import lab2.generateErrorInWord
import lab2.generateRandomWord
import lab4.errors.golay.GolayErrorSolver
import lab4.errors.rm.ReedMullerCode
import lab4.errors.rm.decode
import matrix.distance
import matrix.multiply
import matrix.utils.*
import org.jetbrains.kotlinx.multik.api.identity
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

fun main() {

    val extendedGolayCode = createExtendedGolayCode()
        .out("Расширенный код Голея")

    val checkingMatrixGolay = createCheckingMatrixGolay(extendedGolayCode)
        .out("Проверочная матрица")

    val generatingMatrixGolay = createGeneratingMatrixGolay(extendedGolayCode)
        .out("Порождающая матрица")

    assert(distance(generatingMatrixGolay) == 8)

    println("\n\nИсследование ошибки длиной один")
    checkGolayError(
        generatingSet = generatingMatrixGolay,
        checkingMatrix = checkingMatrixGolay,
        errorSolver = GolayErrorSolver(1)
    )

    println("\n\nИсследование ошибки длиной два")
    checkGolayError(
        generatingSet = generatingMatrixGolay,
        checkingMatrix = checkingMatrixGolay,
        errorSolver = GolayErrorSolver(2)
    )

    println("\n\nИсследование ошибки длиной три")
    checkGolayError(
        generatingSet = generatingMatrixGolay,
        checkingMatrix = checkingMatrixGolay,
        errorSolver = GolayErrorSolver(3)
    )

    println("\n\nИсследование ошибки длиной четыре")
    checkGolayError(
        generatingSet = generatingMatrixGolay,
        checkingMatrix = checkingMatrixGolay,
        errorSolver = GolayErrorSolver(4)
    )

    val rm13 = ReedMullerCode(1, 3)
        .apply {
            generatorMatrix.out("Код РМ 1 3")
        }
    val originalWord13 = listOf(0, 0, 0, 0, 1, 1, 1, 1)
        .toRow()
        .out("Сообщение без ошибки")
    rm13.decode(
        listOf(1, 0, 0, 0, 1, 1, 1, 1)
            .toRow()
            .out("Однократрая ошибка")
    ).isMatchWithWord(originalWord13)
    rm13.decode(
        listOf(1, 0, 0, 0, 0, 1, 1, 1)
            .toRow()
            .out("Двухкратная ошибка")
    ).isMatchWithWord(originalWord13)


    val rm14 = ReedMullerCode(1, 4)
        .apply {
            generatorMatrix.out("Код РМ 1 4")
        }

    val originalWord14 = listOf(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
        .toRow()
        .out("Сообщение без ошибки")
    rm14.decode(
        listOf(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
            .toRow()
            .out("Однократрая ошибка")
    ).isMatchWithWord(originalWord14)
    rm14.decode(
        listOf(1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
            .toRow()
            .out("Двухкратная ошибка")
    ).isMatchWithWord(originalWord14)
    rm14.decode(
        listOf(1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1)
            .toRow()
            .out("Трёхкратная ошибка")
    ).isMatchWithWord(originalWord14)
    rm14.decode(
        listOf(1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0)
            .toRow()
            .out("Четырехкратная ошибка")
    ).isMatchWithWord(originalWord14)


}

fun Row.isMatchWithWord(original: Row): Row {
    println("Исправленная совпадает с изначальным: ${original.toList() == this.toList()}")
    return this
}

fun checkGolayError(generatingSet: Matrix, checkingMatrix: Matrix, errorSolver: GolayErrorSolver) {

    val randomWord = generateRandomWord(generatingSet.rows, generatingSet)
    val erroredWord = generateErrorInWord(randomWord, errorSolver.errorsMatrix(generatingSet.columns))
    val error = errorSolver.findError(erroredWord, checkingMatrix)

    if (error.isEmpty()) {

        println("Ашыбка не нашлась")
        return
    }

    val fixed = (erroredWord + error.toNDArray()).out("Исправленная ошибка")
    println("Исправленная совпадает с изначальным: ${fixed.toList() == randomWord.toList()}")
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
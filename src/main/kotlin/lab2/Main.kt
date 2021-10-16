package lab2

import allWordsForLength
import distance
import matrix.LinearCode
import matrix.utils.*
import multiply
import org.jetbrains.kotlinx.multik.api.identity
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.map
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import wordsForMultiplicity

fun main() {


    first()
}

private fun first() {
    val columns = 7
    val rows = 4
    val distance = 3

    val generatingSet = generateSet(columns, rows, distance)
        .out("G")

    assert(distance(generatingSet) == distance)

    val linearCode = LinearCode(generatingSet)
    val checkingMatrix = linearCode.result
        .out("H")

    val oneLengthErrors = wordsForMultiplicity(
        multiplicity = 1,
        length = columns
    )
    checkError(
        columns = columns,
        rows = rows,
        generatingSet = generatingSet,
        errorProvider = oneLengthErrors,
        checkingMatrix = checkingMatrix,
        block = ::singleError
    )

    println("_________")
    val twoLengthErrors = wordsForMultiplicity(
        multiplicity = 2,
        length = columns
    )
    checkError(
        columns = columns,
        rows = rows,
        generatingSet = generatingSet,
        errorProvider = twoLengthErrors,
        checkingMatrix = checkingMatrix,
        block = ::doubleError
    )
}

private fun generateSet(columns: Int, rows: Int, d: Int): Matrix {

    val identity = mk.identity<Int>(rows)
    val x = allWordsForLength(columns - rows)
        .filter { it.sum() >= d - 1 }
        .shuffled()
        .subList(0, rows)
        .toMatrix()

    return identity.to2DList()
        .mapIndexed { i, row -> row + x[i].toList() }
        .toMatrix()
}

private fun checkError(
    columns: Int,
    rows: Int,
    generatingSet: Matrix,
    errorProvider: Matrix,
    checkingMatrix: Matrix,
    block: (Row, Matrix) -> List<Int>
) {

    val randomWord = generateRandomWord(rows, generatingSet)
    val erroredWord = generateErrorInWord(randomWord, errorProvider)
    val syndromIndexes = block(erroredWord, checkingMatrix)

    val error = List(columns) { if (it in syndromIndexes) 1 else 0 }.toNDArray()
        .out("Найденная ошибка")

    val fixed = (erroredWord + error).out("Исправленная ошибка")
    assert(fixed == randomWord)
}

private fun generateRandomWord(rows: Int, generatingSet: Matrix): Row {
    val identity = mk.identity<Int>(rows)
    // выбираем слово из множества
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

private fun singleError(erroredWord: Row, checkingMatrix: Matrix): List<Int> {
    // проверяем ошибку
    val checkSyndrom = erroredWord.multiply(checkingMatrix)
        .out("Синдром для ошибки")

    val syndromIndex = checkingMatrix
        .to2DList()
        .indexOf(checkSyndrom.toList())

    return when (syndromIndex) {

        -1 -> {
            print("Нет ошыбки")
            emptyList()
        }
        else -> {
            println("Номер в таблице: $syndromIndex")
            listOf(syndromIndex)
        }
    }
}

private fun doubleError(erroredWord: Row, checkingMatrix: Matrix): List<Int> {
    // проверяем ошибку
    val checkSyndrom = erroredWord.multiply(checkingMatrix)
        .out("Синдром для ошибки")
        .toList()

    val checkingList = checkingMatrix.to2DList()
    val syndromIndex = checkingList
        .map { row -> checkingList.indexOfFirst { (it xorPlus row) == checkSyndrom } }
        .mapIndexed { first, second -> first to second }
        .shuffled()
        .first { pair -> pair.second != -1 }.toList()
    if (syndromIndex.isEmpty()) {
        print("Нет ошыбки")
    } else {
        println("Номер в таблице: $syndromIndex")
    }
    return syndromIndex
}


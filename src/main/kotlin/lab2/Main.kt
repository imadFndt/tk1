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
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import wordsForMultiplicity

fun main() {

    // взять слово длины n из слова длины k

    first()
}

private fun first() {
    val columns = 7
    val rows = 4
    val distance = 3

    val identity = mk.identity<Int>(rows)

    val generatingSet = generateSet(columns, rows, distance)
        .out("G")

    assert(distance(generatingSet) == distance)

    val linearCode = LinearCode(generatingSet)
    val checkingMatrix = linearCode.result
        .out("H")

    // все ошибки
    val oneLengthErrors = wordsForMultiplicity(
        multiplicity = 1,
        length = columns
    )

    // выбираем слово из множества
    val chosenIdentity = identity[(0 until rows).random()]
        .out("Выбранная строчка из единичной матрицы")

    val randomWord = chosenIdentity.multiply(generatingSet)
        .out("Полученное слово")

    // вносим ошибку
    val randomError = oneLengthErrors[(0 until randomWord.size).random()]
        .out("Ашыбка")
    val erroredWord = (randomWord + randomError)
        .out("Слово с ошибкой")

    // проверяем ошибку
    val checkSyndrom = erroredWord.multiply(checkingMatrix)
        .out("Синдром для ошибки")

    val syndromIndex = checkingMatrix
        .to2DList()
        .indexOf(checkSyndrom.toList())

    when (syndromIndex) {

        -1 -> {
            print("Нет ошыбки")
            return
        }
        else -> println("Номер в таблице: $syndromIndex")
    }

    val error = List(columns) { if (it == syndromIndex) 1 else 0 }.toNDArray()
        .out("Найденная ошибка")

    val fixed = (erroredWord + error).out("Исправленная ошибка")
    assert(fixed == randomWord)
}

private fun Matrix.out(title: String) = apply {
    println()
    println("$title:")
    println(this)
}

private fun Row.out(title: String) = apply {
    println()
    print("$title: ")
    println(this)
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
package lab6

import matrix.allWordsForLength
import matrix.utils.outTitle
import matrix.utils.to2DList
import matrix.utils.toMatrix
import matrix.wordsForMultiplicity
import java.util.*

fun main() {
    println("Циклический код 7,4")
    println("Тестовый пример")
    solve()
    println("\n\nОшибка кратности 1")
    solve(randomEnabled = true, multiplicity = 1, initialG = listOf(1, 0, 1, 1))
    println("\n\nОшибка кратности 2")
    solve(randomEnabled = true, multiplicity = 2, initialG = listOf(1, 0, 1, 1))
    println("\n\nОшибка кратности 3")
    solve(randomEnabled = true, multiplicity = 3, initialG = listOf(1, 0, 1, 1))

    println("\n\nЦиклический код 15,9")
    println("Тестовый пример")
    println("Пакеты ошибок кратности 3:")
    solve(
        randomEnabled = false, multiplicity = 3,
        initialG = listOf(1, 1, 1, 1, 0, 0, 1),
        m = 15,
        n = 9,
        word = listOf(
            1, 1, 1, 0,
            1, 1, 0, 1,
            0, 1, 0, 1,
            0, 1, 1
        ), error = listOf(
            0, 0, 0, 0,
            0, 0, 0, 0,
            1, 0, 1, 0,
            0, 0, 0
        ),
        isPackaged = true
    )

    println("\n\nИсправление ошибки кратности 1")
    solve(
        randomEnabled = false,
        multiplicity = 3,
        initialG = listOf(1, 0, 0, 1, 1, 1, 1),
        m = 15,
        n = 9,
        isPackaged = true,
        word = listOf(
            1, 1, 1, 0,
            0, 0, 0, 1,
            1, 0, 1, 1,
            0, 0, 0
        ),
        error = listOf(
            0, 0, 0, 0,
            0, 0, 0, 0,
            1, 0, 0, 0,
            0, 0, 0
        )
    )

    println("\n\nИсправление ошибки кратности 2")
    solve(
        randomEnabled = false,
        multiplicity = 3,
        initialG = listOf(1, 0, 0, 1, 1, 1, 1),
        m = 15,
        n = 9,
        isPackaged = true,
        word = listOf(
            1, 1, 1, 0,
            0, 0, 0, 1,
            1, 0, 1, 1,
            0, 0, 0
        ),
        error = listOf(
            0, 0, 0, 0,
            0, 0, 0, 0,
            1, 0, 1, 0,
            0, 0, 0
        )
    )
    println("\n\nИсправление ошибки кратности 3")
    solve(
        randomEnabled = false,
        multiplicity = 3,
        initialG = listOf(1, 0, 0, 1, 1, 1, 1),
        m = 15,
        n = 9,
        isPackaged = true,
        word = listOf(
            1, 1, 1, 0,
            0, 0, 0, 1,
            1, 0, 1, 1,
            0, 0, 0
        ),
        error = listOf(
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            1, 1, 1
        )
    )

    println("\n\nИсправление ошибки кратности 4")
    solve(
        randomEnabled = false,
        multiplicity = 3,
        initialG = listOf(1, 0, 0, 1, 1, 1, 1),
        m = 15,
        n = 9,
        isPackaged = true,
        word = listOf(
            1, 1, 1, 0,
            0, 0, 0, 1,
            1, 0, 1, 1,
            0, 0, 0
        ),
        error = listOf(
            1, 1, 1, 1,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0
        )
    )
}

/**
 * @param multiplicity кратность рандом ошибки
 * @param m длина
 * @param n шырина
 * @param initialG многочлен кода
 * @param word вписывать если выключен рандом, будет словом
 * @param error как выше, только ошибка
 */
private fun solve(
    randomEnabled: Boolean = false,
    multiplicity: Int = 1,
    m: Int = 7,
    n: Int = 4,
    initialG: List<Int> = listOf(1, 1, 0, 1),
    word: List<Int> = listOf(1, 1, 0, 0, 1, 0, 1),
    error: List<Int> = listOf(0, 0, 0, 0, 1, 0, 0),
    isPackaged: Boolean = false
) {
    val g = initialG
        .outPolynomial("g(x) = ")

    val packages = allWordsForLength(multiplicity)
        .filter { it.first() != 0 }
        .map { it.moveRight(0, m) }

    val codedWord = when (randomEnabled) {

        true -> (allWordsForLength(n - 1).random() * g)
        false -> word
    }.apply {
        outTitle("Слово")
        outPolynomial("В виде многочлена:")
    }

    val expectedError = when (randomEnabled) {

        true -> wordsForMultiplicity(multiplicity, m)
            .to2DList()
            .shuffled()
            .first()

        false -> error

    }.apply {
        outTitle("Ошибка")
        outPolynomial("В виде многочлена:")
    }

    val wordWithError = codedWord.zip(expectedError).map { it.first xor it.second }.apply {
        outTitle("Слово с ошибкой")
        outPolynomial("В виде многочлена:")
    }


    val sWords = mutableListOf<List<Int>>()
    val results = mutableListOf<Int>()
    (0 until n).forEach { i ->

        val sWord = wordWithError.moveRight(i, m)
        sWords += (sWord polynomialDivision g).second.moveRight(0, m)
            .outPolynomial("i = $i, $sWord mod $g,  s_i(x)")

        val wt = sWords.last().sum()
        if (i == 0 && sWords.last().all { it == 0 }) {
            println("Ошибка не найдена")
            return
        }

        when (isPackaged) {

            true -> {
                if (sWords.last() in packages) results += i
            }
            false -> {
                if (wt in 1..multiplicity && results.size < multiplicity) results += i
            }
        }
    }

    if (results.isEmpty()) {
        println("Исправление невозможно")
        return
    }

    println("I: $results")

    val errorResult = sWords.mapIndexedNotNull { index, list ->
        when (index in results) {
            true -> list.moveRight(m - index, m)
            else -> null
        }
    }.first().apply {
        println("Пакет: \n${packages.toMatrix()}")
        outTitle("Найденная ошибка:")
        outPolynomial("В виде многочлена")
    }


    val fixed = wordWithError.zip(errorResult).map { it.first xor it.second }
        .apply {
            outTitle("Исправленная ошибка")
            outPolynomial("В виде многочлена")
        }

    println("Исправленная совпадает с изначальным: ${fixed == codedWord}")
}


fun List<Int>.moveRight(rotationRight: Int, expectedSize: Int): List<Int> {
    val result = this.toMutableList() + when (expectedSize > size) {
        true -> List(expectedSize - size) { 0 }
        false -> emptyList()
    }
    Collections.rotate(result, rotationRight)
    return result
}

package lab5

import matrix.allWordsForLength
import kotlin.math.pow

fun v(I: List<Int>, x: List<Int>) = if (I.all { x[it] == 0 }) 1 else 0

fun g(r: Int, m: Int): List<Pair<List<Int>, List<Int>>> {

    val allWords = allWordsForLength(m, true).map { it.reversed() }

    val allI = vectorICombination(r, m)
    val functionTable = allI.map { matrixesWithSameSize ->
        matrixesWithSameSize.map { I ->
            allWords.map { uI -> v(I, uI) } to I
        }
    }

    return functionTable.flatMap { matrixesWithSameSize ->
        matrixesWithSameSize.sortedWith { a, b ->
            a.first.reversed().joinToString { it.toString() }
                .compareTo(b.first.reversed().joinToString { it.toString() })
        }
    }
}

fun vectorICombination(r: Int, m: Int): List<List<List<Int>>> {
    val elements = (0 until m).toList()
    val result = List(r + 1) { mutableListOf<List<Int>>() }
    (0 until 2.0.pow(m).toInt()).forEach huy@{ num ->

        val list = num.toBinaryList()
            .reversed()
            .mapIndexedNotNull { index, i -> if (i == 1) elements[index] else null }
            .toMutableList()

        if (list.size > r) return@huy
        result[list.size] += list
    }
    return result
}
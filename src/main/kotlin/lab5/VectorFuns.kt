package lab5

import matrix.allWordsForLength
import matrix.utils.Row
import matrix.utils.xorPlus
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

fun generateBitmask(r: Int, m: Int): List<List<List<Int>>> {
    val result = List(r + 1) { mutableListOf<List<Int>>() }
    (0 until 2.0.pow(m).toInt()).forEach huy@{ num ->

        val list = num.toBinaryList()
            .toMutableList()

        while (list.size < m) {
            list.add(0, 0)
        }
        val realSize = list.filter { it != 0 }.size
        if (realSize > r) return@huy
        result[realSize] += list
    }
    return result
}

fun majorDecode(r: Int, m: Int, word: List<Int>): List<Int> {
    val words = allWordsForLength(m, true).map { it.reversed() }
    val masks = generateBitmask(r, m)
    var len = 0
    val res = mutableListOf<List<Int>>()
    var wordCopy = word
    (r downTo 0).forEach { i ->
        val arr = mutableListOf<List<Int>>()
        masks[i].forEach { mask ->
            len++
            val I = mask.mapIndexedNotNull { index, i -> if (i == 1) index else null }
            val reversedMask = mask.map { it xor 1 }
            val IC = reversedMask.mapIndexedNotNull { index, i -> if (i == 1) index else null }
            val H = words.filter { v(I, it) == 1 }
            val v_t = H.map { h -> words.map { w -> v(IC, w xorPlus h) } }
            val wWithT = v_t.map { v ->
                v.foldIndexed(0) { index, acc, i ->
                    (acc + i * wordCopy[index]) % 2
                }
            }
            val decision = (wWithT.sum() * 2) > wWithT.size
            if (decision) {
                arr += words.map { x -> v(I, x) }
                res += I
            }
        }
        arr.forEach { x -> wordCopy = wordCopy xorPlus x }
    }
    val answer = List(len) { 0 }.toMutableList()
    val IOrder = g(r, m).map { it.second }
    res.forEach { x ->
        val index = IOrder.indexOf(x)
        answer[index] = 1
    }
    return answer
}
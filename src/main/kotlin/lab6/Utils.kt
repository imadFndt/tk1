package lab6

import matrix.utils.outText
import matrix.utils.outTitle
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.absoluteValue

operator fun List<Int>.times(other: List<Int>): List<Int> {

    val size = this.size + other.size
    val result = MutableList(size) { 0 }

    forEachIndexed { outerIndex, outerItem ->

        other.forEachIndexed { innerIndex, innerItem ->

            result[outerIndex + innerIndex] += outerItem * innerItem
            result[outerIndex + innerIndex] %= 2
        }
    }
    return result
}


infix fun List<Int>.polynomialDivision(divisorIn: List<Int>): Pair<List<Int>, List<Int>> {
    val out = this.reversed().toMutableList()
    val divisor = divisorIn.reversed()
    val normalizer = divisor[0]
    val separator = this.size - divisor.size + 1
    for (i in 0 until separator) {
        out[i] /= normalizer
        val coef = out[i]
        if (coef != 0) {
            for (j in 1 until divisor.size) {
                out[i + j] += -divisor[j] * coef
                out[i + j] %= 2
                out[i + j] = out[i + j].absoluteValue
            }
        }
    }
    return out.subList(0, separator).reversed() to out.subList(separator, out.size).reversed()
}

fun IntRange.randomBinary() = map { ThreadLocalRandom.current().nextInt(0, 2) }


fun List<Int>.toPolynomialString() = mapIndexedNotNull { index, item -> if (item != 0) index to item else null }
    .let {
        if (it.isEmpty()) return@let "0"
        it.joinToString(separator = " + ") { (index, item) ->
            when (index) {
                0 -> item.toString()
                1 -> "x"
                else -> "x^$index"
            }
        }
    }

fun List<Int>.outPolynomial(title: String) = apply {
    toPolynomialString()
        .outText(title)
}

fun List<Int>.outPolynomialTitle(title: String) = apply {
    toPolynomialString()
        .outTitle(title)
}
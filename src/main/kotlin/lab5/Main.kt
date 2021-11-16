package lab5

import matrix.multiply
import matrix.utils.out
import matrix.utils.toMatrix
import matrix.utils.toRow

fun main() {

    val m = 4
    val r = 2

    val generating = g(r, m).map { it.first }.toMatrix()
    println(g(r, m).joinToString(separator = "\n"))
    val word = listOf(0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0)
    val u = majorDecode(r, m, word).toRow()
    word.toRow().out("w")
    u.out("u")
    u.multiply(generating).out("v")

}


fun Int.toBinaryList() = toString(2).map(Character::getNumericValue)
package lab5

fun main() {

    val m = 4
    val r = 3

    val generating = g(r, m)
    println(g(r, m).joinToString(separator = "\n"))
}


fun Int.toBinaryList() = toString(2).map(Character::getNumericValue)
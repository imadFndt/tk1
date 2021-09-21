import utils.TestMatrix

fun main() {

    // 1.1
    println(TestMatrix.array.ref().toString())
    println()
    // 1.2
    println(TestMatrix.array.rref().toString())
    println()
    // 1.3
    println(LinearCode(TestMatrix.array).result)
}

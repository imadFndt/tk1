import org.jetbrains.kotlinx.multik.ndarray.operations.reduce
import utils.TestMatrix
import utils.to2DList

fun main() {

    // 1.1
    println(TestMatrix.array.ref().toString())
    println()
    // 1.2
    println(TestMatrix.array.rref().toString())
    println()
    // 1.3
    println(LinearCode(TestMatrix.array).result)
    // 1.4

    val firstSet = firstMethod(TestMatrix.array3)
    println("First Set")
    println(firstSet.toString())

    val secondSet = secondMethod(TestMatrix.array3)

    println("Second Set")
    println(secondSet)

    val firstList = firstSet.to2DList()
    val secondList = secondSet.to2DList()

    println(
        "First set equals second set: ${firstList.containsAll(secondList) && secondList.containsAll(firstList)}"
    )
    println("First * check matrix is zero: ${firstSet.myMultiply(TestMatrix.arrayH).reduce { acc, i -> acc + i } == 0}")
    println(
        "Second * check matrix is zero: ${
            secondSet.myMultiply(TestMatrix.arrayH).reduce { acc, i -> acc + i } == 0
        }")


    val t = distance(secondSet)
    val d = t - 1
    println("t = $t ")
    println("d = $d ")

}

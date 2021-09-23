import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.operations.reduce
import utils.TestMatrix
import utils.columns
import utils.to2DList
import utils.xorPlus

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
    fourth()

    fifth()
}

private fun fourth() {
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
}

private fun fifth() {
    val words = secondMethod(TestMatrix.array3)
    val checkingMatrix = TestMatrix.arrayH

    val t = distance(words)

    val errors = wordsForMultiplicity(1, words.columns).to2DList()

    println("Errors")
    val chosenError = errors[7]
    words.to2DList().forEach { word ->
        val errored = word xorPlus chosenError
        println("___For word: $word, error: $chosenError row: $errored")
        val checkResult = errored.toNDArray().multiply(checkingMatrix)
        println("errored * H: $checkResult")
        println()
    }
    val d = t - 1
    println("t = $t ")
    println("d = $d ")
}

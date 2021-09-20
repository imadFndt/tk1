import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray

fun main() {
    val array: OldMatrix = arrayOf(
        intArrayOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
        intArrayOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
        intArrayOf(1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1),
    )

    val array2: OldMatrix = arrayOf(
        intArrayOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
        intArrayOf(1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
        intArrayOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1),
    )

    val array3: OldMatrix = arrayOf(
        intArrayOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 1, 1, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
        intArrayOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1),
    )

    val array4: OldMatrix = arrayOf(
        intArrayOf(1, 0, 1, 1),
        intArrayOf(1, 0, 1, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 1),
        intArrayOf(0, 1, 0, 0),
    )

    val mArray1 = mk.ndarray(
        listOf(
            listOf(1, 1, 1, 1),
            listOf(1, 0, 1, 1),
            listOf(0, 0, 0, 1),
            listOf(1, 0, 1, 1),
            listOf(1, 1, 0, 1),
        )
    )
    println(mArray1.rref().toString())
}

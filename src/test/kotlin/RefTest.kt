import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.operations.mapIndexed
import org.jetbrains.kotlinx.multik.ndarray.operations.sum
import org.junit.Test
import kotlin.test.assertTrue

class RefTest {

    @Test
    fun `check ref`() {
        val input: OldMatrix = arrayOf(
            intArrayOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
            intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1),
            intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
            intArrayOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
            intArrayOf(1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
        )
        val outOld = input.ref().onEach { row ->
            row.forEach { print("$it, ") }
            println()
        }
        println()
        println()
        val inputNd = mk.ndarray(
            listOf(
                listOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
                listOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1),
                listOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
                listOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
                listOf(1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
            )
        )

        val outPutNd = inputNd.ref()
        println(outPutNd)

        val isEqual = outPutNd.mapIndexed { index, item ->

            val (row, col) = index
            if (outOld[row][col] == item) {
                0
            } else {
                1
            }
        }

        assertTrue { isEqual.sum() == 0 }
    }
}

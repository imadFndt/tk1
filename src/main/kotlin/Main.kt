typealias Matrix = Array<Row>
typealias Row = IntArray

fun main() {
    val array: Matrix = arrayOf(
        intArrayOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
        intArrayOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
        intArrayOf(1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1),
    )

    val array2: Matrix = arrayOf(
        intArrayOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
        intArrayOf(1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
        intArrayOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1),
    )

    val array3: Matrix = arrayOf(
        intArrayOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 1, 1, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
        intArrayOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1),
    )

    array2.ref().forEach { println(it.toList()) }
}

fun Matrix.ref(): Matrix {
    val result = copy()
    var row = 0
    (0 until columns).forEach { i ->
        val index = result.findFirstOneUnder(column = i, row)
        when {
            index == row -> {
                result.destroyOnes(i, index)
                row++
            }
            index == -1 -> Unit
            index > row -> {
                result.swapRows(row, index)
                result.destroyOnes(i, row)
                row++
            }
        }
    }
    return result
}

fun Matrix.swapRows(destinationIndex: Int, sourceIndex: Int) = let { result ->
    val sourceRow = result[sourceIndex].copyOf()
    result[sourceIndex] = result[destinationIndex]
    result[destinationIndex] = sourceRow
}

private fun Matrix.findFirstOneUnder(column: Int, row: Int) = column(column).mapIndexed { index, item ->
    if (index >= row && item == 1) index else null
}.filterNotNull()
    .firstOrNull() ?: -1

private fun Matrix.destroyOnes(i: Int, index: Int) {
    onesInColumn(i, index).forEach { rowIndex ->
        val a = this[rowIndex] + this[index]
        this[rowIndex] = a
    }
}

private fun Matrix.onesInColumn(column: Int, underRowIndex: Int = 0) = column(column)
    .mapIndexed { index, item ->

        when (item) {

            1 -> index
            else -> null
        }
    }
    .filterIndexed { index, item -> index > underRowIndex }
    .filterNotNull()

fun Matrix.copy() = copyOf().also { copy ->
    forEachIndexed { i, arr ->
        copy[i] = arr.copyOf()
    }
}

val Matrix.rows get() = size
val Matrix.columns get() = this[0].size

fun Matrix.column(column: Int) = IntArray(rows) { this[it][column] }

operator fun Row.plus(array: Row) = copyOf().also { copy ->
    array.forEachIndexed { index, item ->
        copy[index] = copy[index] xor item
    }
}

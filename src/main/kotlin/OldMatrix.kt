typealias OldMatrix = Array<OldRow>
typealias OldRow = IntArray


fun OldMatrix.ref(): OldMatrix {
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

fun OldMatrix.swapRows(destinationIndex: Int, sourceIndex: Int) = let { result ->
    val sourceRow = result[sourceIndex].copyOf()
    result[sourceIndex] = result[destinationIndex]
    result[destinationIndex] = sourceRow
}

private fun OldMatrix.findFirstOneUnder(column: Int, row: Int) = column(column).mapIndexed { index, item ->
    if (index >= row && item == 1) index else null
}.filterNotNull()
    .firstOrNull() ?: -1

private fun OldMatrix.destroyOnes(i: Int, index: Int) {
    onesInColumn(i, index).forEach { rowIndex ->
        val a = this[rowIndex] + this[index]
        this[rowIndex] = a
    }
}

private fun OldMatrix.onesInColumn(column: Int, underRowIndex: Int = 0) = column(column)
    .mapIndexed { index, item ->

        when (item) {

            1 -> index
            else -> null
        }
    }
    .filterIndexed { index, item -> index > underRowIndex }
    .filterNotNull()

fun OldMatrix.copy() = copyOf().also { copy ->
    forEachIndexed { i, arr ->
        copy[i] = arr.copyOf()
    }
}

val OldMatrix.rows get() = size
val OldMatrix.columns get() = this[0].size

fun OldMatrix.column(column: Int) = IntArray(rows) { this[it][column] }

operator fun OldRow.plus(array: OldRow) = copyOf().also { copy ->
    array.forEachIndexed { index, item ->
        copy[index] = copy[index] xor item
    }
}
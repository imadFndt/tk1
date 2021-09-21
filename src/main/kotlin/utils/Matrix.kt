package utils

import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.mapIndexed
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

typealias Matrix = D2Array<Int>
typealias Row = MultiArray<Int, D1>

val Matrix.rows get() = this.shape.component1()
val Matrix.columns get() = this.shape.component2()

operator fun Row.plus(array: Row) = mapIndexed { index: Int, item: Int ->
    item xor array[index]
}

fun Matrix.swapRows(destinationIndex: Int, sourceIndex: Int) = let { result ->
    val sourceRow = result[sourceIndex].deepCopy()
    result[sourceIndex] = result[destinationIndex]
    result[destinationIndex] = sourceRow
}

fun Matrix.to2DList() = (0 until rows).map {
    this[it].toList()
}

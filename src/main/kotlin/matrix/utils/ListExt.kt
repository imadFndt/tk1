package matrix.utils

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray

typealias CollectionMatrix = List<List<Int>>

fun CollectionMatrix.toMatrix(): Matrix = mk.ndarray(this)

fun List<Int>.toRow(): Row = mk.ndarray(this)

infix fun List<Int>.xorPlus(array: List<Int>) = mapIndexed { index: Int, item: Int ->
    item xor array[index]
}
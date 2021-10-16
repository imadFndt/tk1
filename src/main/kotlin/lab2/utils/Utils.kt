package lab2.utils

import allWordsForLength
import matrix.utils.Matrix
import matrix.utils.to2DList
import matrix.utils.toMatrix
import org.jetbrains.kotlinx.multik.api.identity
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.toList


fun generateSimpleSet(columns: Int, rows: Int, d: Int): Matrix {

    val identity = mk.identity<Int>(rows)
    val x = allWordsForLength(columns - rows)
        .filter { it.sum() >= d - 1 }
        .shuffled()
        .subList(0, rows)
        .toMatrix()

    return identity.to2DList()
        .mapIndexed { i, row -> row + x[i].toList() }
        .toMatrix()
}
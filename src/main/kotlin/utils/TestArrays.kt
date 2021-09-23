package utils

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray

object TestMatrix {
    val array: Matrix = mk.ndarray(
        listOf(
            listOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
            listOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
            listOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
            listOf(1, 0, 1, 0, 1, 1, 1, 0, 0, 0),
            listOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1),
        )
    )


    val array2: Matrix = mk.ndarray(
        listOf(
            listOf(1, 0, 1, 1),
            listOf(1, 0, 1, 0),
            listOf(0, 0, 0, 0),
            listOf(0, 0, 0, 1),
            listOf(0, 1, 0, 0),
        )
    )

    val array3: Matrix = mk.ndarray(
        listOf(
            listOf(1, 0, 1, 1, 0, 0, 0, 1, 0, 0),
            listOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
            listOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 1, 0, 0, 1),
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 1, 1),
        )
    )

    val arrayH: Matrix = mk.ndarray(
        listOf(
            listOf(0, 1, 1, 1, 1),
            listOf(1, 0, 0, 0, 0),
            listOf(0, 1, 0, 0, 0),
            listOf(0, 0, 1, 0, 1),
            listOf(0, 0, 0, 1, 0),
            listOf(0, 0, 1, 0, 0),
            listOf(0, 0, 0, 0, 1),
            listOf(0, 0, 0, 1, 0),
            listOf(0, 0, 0, 0, 1),
            listOf(0, 0, 0, 0, 1),
        )
    )
}
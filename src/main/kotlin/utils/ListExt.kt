package utils

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray

fun List<List<Int>>.toMatrix(): Matrix = mk.ndarray(this)
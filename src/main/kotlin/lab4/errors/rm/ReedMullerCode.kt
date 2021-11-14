package lab4.errors.rm

import matrix.multiply
import matrix.utils.*
import org.jetbrains.kotlinx.multik.api.empty
import org.jetbrains.kotlinx.multik.api.identity
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.map
import org.jetbrains.kotlinx.multik.ndarray.operations.times
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import kotlin.math.absoluteValue
import kotlin.math.pow


class ReedMullerCode(r: Int, val m: Int) {
    val generatorMatrix: Matrix

    init {
        require(r <= m)

        fun createGeneratorMatrixOn(r: Int, m: Int): Matrix {

            return when (r) {

                0 -> listOf(List(2 shl m - 1) { 1 }).toMatrix()
                m -> {

                    val previousStep = createGeneratorMatrixOn(m - 1, m)
                    val bottomOfMatrix = listOf(List(previousStep.rows) { 0 } + 1)
                    (previousStep.to2DList().plus(bottomOfMatrix)).toMatrix()
                }
                in 1 until m -> {

                    val previousMStep = createGeneratorMatrixOn(r, m - 1).to2DList()
                    var rows: Int
                    var columns: Int
                    val previousRStep = createGeneratorMatrixOn(r - 1, m - 1).apply {
                        rows = this.rows
                        columns = this.columns
                    }.to2DList()
                    val topSide = previousMStep.mapIndexed { index, row ->
                        row + previousMStep[index]
                    }
                    val empty = mk.empty<Int, D2>(rows, columns).to2DList()

                    val bottomSide = empty.mapIndexed { index, row ->
                        row + previousRStep[index]
                    }
                    return (topSide + bottomSide).toMatrix()
                }
                else -> throw IllegalArgumentException()
            }
        }

        generatorMatrix = createGeneratorMatrixOn(r, m)
    }
}

private infix fun Matrix.kroneckerMultiply(b: Matrix): Matrix {
    val a = this
    val multiplied = a.to2DList().map { list -> list.map { b.times(it).to2DList() } }
    return multiplied.flatMap { rowsMatrices ->
        (rowsMatrices.first().indices).map { rowIndex ->
            rowsMatrices.flatMap { it[rowIndex] }
        }
    }.toMatrix()
}

fun findReedMullerH(i: Int, m: Int): Matrix {
    val left = mk.identity<Int>(2.0.pow(m - i).toInt())
    val right = mk.identity<Int>(2.0.pow(i - 1).toInt())

    val H = listOf(
        listOf(1, 1),
        listOf(1, -1)
    ).toMatrix()
    return left kroneckerMultiply H kroneckerMultiply right
}

fun ReedMullerCode.decode(word: Row): Row {
    val erroredWord = word
        .out("Слово с ошибкой")

    val mapped: Row = erroredWord.map {
        when (it == 0) {
            true -> -1
            false -> it
        }
    }.out("w̅")

    val wS = (1..this.m).fold(mapped) { acc, i ->

        acc.multiply(
            other = findReedMullerH(i, m),
            useXor = false
        ).out("w${i}")
    }.toList()

    val index = wS.indices.maxByOrNull { wS[it].absoluteValue }!!

    val vj = index.toString(2).map(Character::getNumericValue)
        .let { List(m - it.size) { 0 } + it }
        .reversed()

    val message = when {
        wS[index] > 0 -> listOf(1) + vj
        wS[index] < 0 -> listOf(0) + vj
        else -> emptyList()
    }
    print("Исходное сообщение равно: $message")
    return listOf(message)
        .toMatrix()[0]
        .multiply(generatorMatrix)
        .out("Исправленное слово")
}
package matrix

import org.jetbrains.kotlinx.multik.api.identity
import org.jetbrains.kotlinx.multik.api.mk
import matrix.utils.*

class LinearCode(val matrix: Matrix) {
    val result: Matrix

    val n: Int
    val k: Int

    init {
        val generatorMatrix = matrix.rref().apply {
            n = columns
            k = rows
        }.to2DList()
        val leadColumns = generatorMatrix.leadColumnsIndex()
        val X = generatorMatrix.filterLeadColumns(leadColumns)
        val I = identityMatrix(n - k).toMutableList()
        leadColumns.forEachIndexed { index, i ->
            I.add(i, X[index].toList())
        }
        result = I.toMatrix()
    }

}

fun List<List<Int>>.leadColumnsIndex(): List<Int> = map { row -> row.indexOfFirst { it == 1 } }.filterNot { it == -1 }

fun List<List<Int>>.filterLeadColumns(lead: List<Int>) =
    map { row -> row.filterIndexed { index, _ -> !lead.contains(index) } }

fun identityMatrix(size: Int) = mk.identity<Int>(size).to2DList()

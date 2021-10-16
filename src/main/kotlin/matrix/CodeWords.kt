import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.r
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.reduce
import org.jetbrains.kotlinx.multik.ndarray.operations.times
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import matrix.utils.*
import kotlin.math.pow


fun firstMethod(generatingSet: Matrix): Matrix {
    val collectionMatrix = generatingSet.to2DList().toMutableList()


    for (i in 0 until collectionMatrix.size) {
        for (j in i until collectionMatrix.size) {
            val sum = collectionMatrix[i] xorPlus collectionMatrix[j]
            if (collectionMatrix.contains(sum).not()) collectionMatrix.add(sum)
        }
    }

    return collectionMatrix.toMatrix()
        .filterZerosRows()
}

fun secondMethod(generatingSet: Matrix): Matrix {
    return allWordsForLength(generatingSet.rows).toMatrix().myMultiply(generatingSet)
}

fun Matrix.myMultiply(other: Matrix): Matrix {
    require(this.columns == other.rows)
    return (0 until rows)
        .map { mainRow ->

            this[mainRow, 0.r..columns].multiply(other).toList()
        }.toMatrix()
}

fun Row.multiply(other: Matrix): Row {
    val result = mk.d1array(other.columns) { 0 }
    (0 until other.columns).forEach { otherColumn ->

        val column = other[0.r..other.rows, otherColumn]
        result[otherColumn] = (this * column).reduce { acc, i -> acc xor i }
    }
    return result
}


fun allWordsForLength(n: Int): CollectionMatrix {

    val allBinaryCombinations = (2.0.pow(n) - 1).toInt()
    return (1..allBinaryCombinations).map { index ->

        val indexValue = index.toString(2)
            .map(Character::getNumericValue)

        List(n - indexValue.size) { 0 } + indexValue
    }
}

fun wordsForMultiplicity(multiplicity: Int, length: Int): Matrix {
    if (multiplicity == 0) return mk.ndarray(listOf(List(length) { 0 }))
    return allWordsForLength(n = length)
        .filter { it.reduce { acc, i -> acc + i } == multiplicity }
        .toMatrix()
}
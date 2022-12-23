import java.io.File
import java.math.BigInteger

fun floorMod(x: BigInteger, y: BigInteger): BigInteger {
    // Stolen from the Java.math library and updated to work with BigIntegers

    val r = x % y
    // if the signs are different and modulo not zero, adjust result
    return if (x xor y < BigInteger.ZERO && r != BigInteger.ZERO) {
        r + y
    } else r
}

fun MutableList<Num>.addRingBuffered(index: BigInteger, newValue: Num): Int {
    val newIndex = floorMod(index, this.size.toBigInteger()).toInt()

    this.add(newIndex, newValue)
    return newIndex
}

fun MutableList<Num>.getRingBuffered(index: Int): Num {
    val newIndex = index % this.size
    return this[newIndex]
}

private operator fun Int.times(multiplier: BigInteger): BigInteger {
    return this.toBigInteger() * multiplier
}

private operator fun Int.plus(value: BigInteger): BigInteger {
    return this.toBigInteger() + value
}

class Num(val value: BigInteger)

fun doPart(part: Int) {
    val multiplier = (if (part == 1) 1 else 811589153).toBigInteger()
    val repeats = if (part == 1) 1 else 10

    val initialList = File("input.txt").readLines().map { Num(it.toInt() * multiplier) }
    val ringBuffer = initialList.toMutableList()

    (1..repeats).forEach { _ ->
        initialList.forEach { number ->
            val index = ringBuffer.indexOf(number)
            val newIndex = index + number.value
            ringBuffer.removeAt(index)
            ringBuffer.addRingBuffered(newIndex, number)
        }
    }
    val indexOf0 = ringBuffer.indexOfFirst { it.value == BigInteger.ZERO }
    val total = arrayOf(1000, 2000, 3000).sumOf { ringBuffer.getRingBuffered(indexOf0 + it).value }

    println("Part $part: $total")
}

fun main() {
    doPart(1)
    doPart(2)
}
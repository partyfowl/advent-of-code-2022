import java.io.File
import java.lang.Exception
import java.math.BigInteger
import kotlin.math.pow

fun Map<String, String>.getMonkeyShout(key: String): BigInteger {
    val value = this[key]!!
    value.toBigIntegerOrNull()?.let {
        // If it's an int value already, we return it
        return it
    }

    val (first, operator, second) = value.split(' ')
    val firstInt = this.getMonkeyShout(first)
    val secondInt = this.getMonkeyShout(second)
    when (operator) {
        "*" -> return firstInt * secondInt
        "/" -> return firstInt / secondInt
        "+" -> return firstInt + secondInt
        "-" -> return firstInt - secondInt
    }
    throw Exception()
}

fun Map<String, String>.expandEquation(key: String, vararg doNotExpand: String): String {
    if (doNotExpand.contains(key)) return key

    return this.getMonkeyShout(key).toString()
}

fun main() {
    val map = File("input.txt").readLines().associate {
        val (k, v) = it.split(": ")
        k to v
    }.toMutableMap()

    val rootValue = map.getMonkeyShout("root")
    println("Part 1: $rootValue")

    print("\t\t")

    val fixed = map.getMonkeyShout("zfhn")


    val min = 37572720
    val max = 37572730
    val exp = 5

    val minBig = BigInteger.TEN.pow(exp).times(min.toBigInteger())
    val maxBig = BigInteger.TEN.pow(exp).times(max.toBigInteger())
    val diff = minBig - maxBig
    println(diff)
    min.toBigInteger()

    for ( it in 1..1000000) {
        map["humn"] = (minBig + it.toBigInteger()).toString()
        val shoutValue = map.getMonkeyShout("jgtb")
        println(fixed - shoutValue)
        if (shoutValue == fixed) {
            println("Part 2: ${map["humn"]}")
            break
        }
    }

}
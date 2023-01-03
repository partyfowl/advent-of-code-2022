import java.io.File
import kotlin.Exception

fun main() {
    var lines = File("input.txt").readLines()

    val instruction = lines.last()

    lines = lines.dropLast(2) // Not including instruction/blank lines

    val width = lines[0].length
    val height = lines.size

    // Initial coordinates
    var x = lines[0].indexOf('.')
    var y = 0

    // Multiplicative modifiers to set direction
    var xMod = 1
    var yMod = 0


    val map = lines.map {line ->
        (0 until width).map {
            if (it < line.length) return@map line[it] else ' '
        }
    }

    val re = """(\d+)(\w?)""".toRegex()

    re.findAll(instruction).forEach { match ->
        // Move Phase
        val distance = match.groupValues[1].toInt()

        for (i in 1..distance) {

            // Keeping a placeholder of where we started
            val currentX = x
            val currentY = y

            // Ring buffer situation here
            do {
                x = Math.floorMod(x + xMod, width)
                y = Math.floorMod(y + yMod, height)
            } while (map[y][x] == ' ')

            if (map[y][x] == '#') {
                x = currentX
                y = currentY
                break
            }
        }

        // Turn Phase
        val (currentXMod, currentYMod) = Pair(xMod, yMod)
        when (match.groupValues[2]) {
            "R" -> {
                xMod = if (currentXMod != 0) 0 else -currentYMod
                yMod = if (currentYMod != 0) 0 else currentXMod

            }
            "L" -> {
                xMod = if (currentXMod != 0) 0 else currentYMod
                yMod = if (currentYMod != 0) 0 else -currentXMod
            }
        }
    }

    val column = x + 1
    val row = y + 1

    val directionValue = when (Pair(xMod, yMod)) {
        Pair(1, 0) -> 0
        Pair(0, 1) -> 1
        Pair(-1, 0) -> 2
        Pair(0, -1) -> 3
        else -> {
            throw Exception("Invalid xMod/yMod values")
        }
    }

    val answer = 1000 * row + 4 * column + directionValue

    println("Part 1: $answer")

}
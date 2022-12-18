import java.io.File
import kotlin.math.abs

typealias Cubes=List<List<Int>>

fun doPart1(cubes: Cubes) {
    var total = 0

    for ((x, y, z) in cubes) {
        // Assume we've got 6 exposed sides until proven otherwise
        total += 6

        // Check each of the other cubes
        for ((x2, y2, z2) in cubes) {
            // If 2 of the coordinates match, and the non-matching coordinate is 1 higher/lower,
            // then the cube has 1 fewer exposed side than we initially thought.
            if (arrayOf(x==x2, y==y2, z==z2).count { it } == 2) if (abs(x + y + z - x2 - y2 - z2) == 1) total -= 1
        }
    }
    println("Part 1: $total")
}

fun main() {
    val cubes = File("input.txt").readLines().map { line -> line.split(',').map { it.toInt() } }

    doPart1(cubes)
}
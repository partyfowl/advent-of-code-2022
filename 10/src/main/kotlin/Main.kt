import java.io.File


fun solve(fileName: String): Pair<Int, String> {
    var index = 1
    var register = 1
    var sumSignalStrength = 0
    var rendered = ""
    var newChar: Char

    File(fileName).forEachLine { line ->
        val splitLine = line.split(' ')
        val cycles = splitLine.size

        (1..cycles).forEach { _ ->
            if (index % 40 == 20) sumSignalStrength += register * index
            newChar = if ((index-1) % 40 in register-1..register+1) '#' else '.'
            rendered += newChar
            if (index % 40 == 0) rendered += '\n'
            index++
        }

        if (cycles == 2) {
            register += splitLine[1].toInt()
        }
    }
    return Pair(sumSignalStrength, rendered.trim())
}

fun main() {
    val (part1, part2) = solve("input.txt")
    println("Part 1: $part1")
    println("Part 2:\n$part2")
}
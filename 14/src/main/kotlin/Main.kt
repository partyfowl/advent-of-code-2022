import java.io.File

private fun <T> List<T>.toPair(): Pair<T, T> {
    return Pair(this[0], this[1])
}

private fun plotLine(point1: Pair<Int, Int>, point2: Pair<Int, Int>): List<Pair<Int, Int>> {

    val (minX, maxX) = listOf(point1.first, point2.first).sorted()
    val (minY, maxY) = listOf(point1.second, point2.second).sorted()

    return if (minX != maxX) (minX..maxX).map { Pair(it, minY) }
    else (minY..maxY).map { Pair(minX, it) }

}

fun sandfall(grid: MutableList<MutableList<Char>>, startingPoint: Pair<Int, Int>) {
    var (x, y) = startingPoint

    while (true) {
        if (grid[x][y + 1] == '.') {
            y++
        }
        else if (grid[x-1][y+1] == '.') {
            // Down and left
            y++
            x--
        }
        else if (grid[x+1][y+1] == '.') {
            // Down and right
            y++
            x++
        }
        // Nowhere to go, so the sand stops here and we return
        else {
            grid[x][y] = 'o'
            return
        }
    }

}


fun doPart(part: Int) {

    val re = """(\d+),(\d+)""".toRegex()

    val rocks = mutableSetOf<Pair<Int, Int>>()

    val minX = 0 // Guesswork
    val maxX = 1000 // Guesswork
    val minY = 0 // This is a fixed maximum height
    var maxY = 0 // This is not fixed, we need to find this

    File("input.txt").forEachLine { line ->

        val linePoints = re.findAll(line).map {match -> match!!.groupValues.takeLast(2).map { it.toInt() }.toPair()}.toList()

        (0 until linePoints.size-1).forEach {index ->

            // We need to check every point's Y value to find the maximum Y value
            if (linePoints[index].second > maxY) maxY = linePoints[index].second

            if (index == linePoints.size-1) return@forEach // This is to stop an IndexOutOfBoundsException

            rocks.addAll(plotLine(linePoints[index], linePoints[index+1]))
        }
    }

    maxY += 2 // This is so that we create the floor in the correct place

    val grid = mutableListOf<MutableList<Char>>()

    for (x in minX..maxX) {
        grid.add(mutableListOf())
        for (y in minY..maxY) {

            // Lazy floor function
            if ((part == 2) and (y == maxY)) grid.last().add('F')

            else {
                val char = if (rocks.contains(Pair(x, y))) '#' else '.'
                grid.last().add(char)
            }
        }
    }

    val startPosition = Pair(500-minX, 0) // Offsetting by minX so that we can access easily in the grid

    var sandCount = 0
    while (true) {
        val (x, y) = startPosition
        if (grid[x][y] == 'o') break
        try {
            sandfall(grid, startPosition)
        } catch (e: IndexOutOfBoundsException) {
            break
        }
        sandCount++
    }
    println("Part $part: $sandCount")

}

fun main() {
    doPart(1)
    doPart(2)
}


import java.io.File

fun IntRange.contains(range: IntRange): Boolean {
    return this.contains(range.first()) and this.contains(range.last())
}

fun main() {
    var re = """^(\d+)-(\d+),(\d+)-(\d+)$""".toRegex()

    var containsCount = 0
    var intersectCount = 0

    File("input.txt").forEachLine { line ->
        if (line != ""){
            var match = re.matchEntire(line)
            var rangeValues = match?.groupValues!!.takeLast(4).map { it.toInt() }

            var range1 = rangeValues[0]..rangeValues[1]
            var range2 = rangeValues[2]..rangeValues[3]

            if (range1.contains(range2) or range2.contains(range1)) {
                containsCount++
            }

            if (range1.intersect(range2).isNotEmpty()) {
                intersectCount++
            }
        }
    }
    println("Part 1: $containsCount")
    println("Part 2: $intersectCount")
}

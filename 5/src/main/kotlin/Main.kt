import java.io.File


fun getNumCrates(line: String): Int {
    return (line.length + 1) / 4
}

fun initCrateArray(size: Int): Array<MutableList<Char>> {
    return Array(size) { ArrayList<Char>() }
}

fun MutableList<Char>.prepend(element: Char){
    this.add(0, element)
}

fun main(){
    for (part in 1..2) doPart(part)
}

fun doPart(part: Int) {
    val lines = File("input.txt").readLines()

    val re = """^move (\d+) from (\d+) to (\d+)$""".toRegex()

    val crateArray: Array<MutableList<Char>> = initCrateArray(getNumCrates(lines[0]))

    var index = 0

    while ('[' in lines[index]){
        lines[index].forEachIndexed { i, char ->
            if (char in 'A'..'Z'){
                crateArray[i/4].prepend(char)
            }
        }
        index++
    }

    for (line in lines.drop(index+2)) {
        val (num, src, dst) = re.matchEntire(line)!!.groupValues.takeLast(3).map { it.toInt() }
            var cratesToMove = crateArray[src - 1].takeLast(num)
            if (part == 1) cratesToMove = cratesToMove.asReversed()
            crateArray[dst - 1].addAll(cratesToMove)
            for (i in 1..num) {
                crateArray[src - 1].removeLast()
            }
    }

    var topCrates = ""

    for (cratePile in crateArray){
        topCrates += cratePile.last()
    }
    println("Part $part: $topCrates")
}

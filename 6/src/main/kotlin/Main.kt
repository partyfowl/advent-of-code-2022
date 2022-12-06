import java.io.File

fun getMarkerIndex(data: String, length: Int): Int{
    for (i in 0..data.length-length) if (data.slice(i until i+length).toCharArray().distinct().size == length) return i+length
    return 0
}

fun main() {
    val data = File("input.txt").readText()
    println("Part 1: ${getMarkerIndex(data, 4)}")
    println("Part 2: ${getMarkerIndex(data, 14)}")
}

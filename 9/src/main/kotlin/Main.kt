import java.io.File
import kotlin.math.abs

class Rope(size: Int=2){
    private val knots = Array(size) { mutableListOf(0, 0) }

    private val head = knots.first()
    private val tail = knots.last()

    val tailPositions = mutableSetOf<Pair<Int, Int>>()

    private fun logTailPosition(){
        tailPositions.add(Pair(tail[0], tail[1]))
    }

    init {
        logTailPosition()
    }

    private fun moveKnot(knot1: MutableList<Int>, knot2: MutableList<Int>){
        val xDiff = knot1[0] - knot2[0]
        val yDiff = knot1[1] - knot2[1]

        // If we don't need to move the tail, just exit
        if ((abs(xDiff) <= 1) and (abs(yDiff) <= 1)) return

        if (xDiff > 0) knot2[0] += 1
        else if (xDiff < 0) knot2[0] -= 1

        if (yDiff > 0) knot2[1] += 1
        else if (yDiff < 0) knot2[1] -= 1
    }

    fun move(direction: Char, distance: Int){
        val index = if (direction in setOf('L', 'R')) 0 else 1
        val multiplier = if (direction in setOf('U', 'R')) 1 else -1
        repeat(distance){
            head[index] += multiplier
            (1 until knots.size).forEach {
                moveKnot(knots[it-1], knots[it])
            }
            logTailPosition()

        }
    }
}

fun main() {
    val rope = Rope()
    val longRope = Rope(10)

    File("input.txt").forEachLine {
        val (direction, distance) = it.split((' '))
        rope.move(direction[0], distance.toInt())
        longRope.move(direction[0], distance.toInt())
    }

    println("Part 1: ${rope.tailPositions.size}")
    println("Part 2: ${longRope.tailPositions.size}")
}
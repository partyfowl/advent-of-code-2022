import java.io.File
import java.math.BigInteger
import kotlin.math.abs


private class Beacon(val x: Int, val y: Int)

private class Sensor(val x: Int, val y: Int, beaconX: Int, beaconY: Int){
    val beacon = Beacon(beaconX, beaconY)

    val manhattanDistance = abs(x-beacon.x) + abs(y-beacon.y)

    fun inCoverage(x: Int, y: Int): Boolean {
        return (abs(x - this.x) + abs(y - this.y)) <= manhattanDistance
    }

    fun getStartOfCoverage(y: Int): Int {
        val yDiff = abs(y - this.y)
        val xDiff = manhattanDistance - yDiff
        return  x - xDiff
    }
}

private fun doPart2(sensors: List<Sensor>) {


    for (y in 4000000 downTo 0) {
        var x = 4000000

        while (x >= 0) {
            var beaconIsHere = true
            for (sensor in sensors) if (sensor.inCoverage(x, y)) {
                beaconIsHere = false
                x = sensor.getStartOfCoverage(y)
                break
            }
            if (beaconIsHere) {
                val answer: BigInteger = x.toBigInteger() * 4000000.toBigInteger() + y.toBigInteger()
                println("Part 2: $answer")
                return
            }
            x--
        }
    }
    println("Did not find :(")
}

fun main() {
    // Setup

    val re = """x=(-?\d+), y=(-?\d+).*x=(-?\d+), y=(-?\d+)""".toRegex()

    var minX = 0
    var maxX = 0
    var minY = 0
    var maxY = 0

    val sensors = File("input.txt").readLines().map { line ->
        val match = re.find(line)
        val (x, y, beaconX, beaconY) = match!!.groupValues.takeLast(4).map { it.toInt() }
        val sensor = Sensor(x, y, beaconX, beaconY)

        minX = minOf(minX, x-sensor.manhattanDistance, beaconX)
        maxX = maxOf(maxX, x+sensor.manhattanDistance, beaconX)
        minY = minOf(minY, y-sensor.manhattanDistance, beaconY)
        maxY = maxOf(maxY, y+sensor.manhattanDistance, beaconY)

        sensor
    }



    // Part 1

    val y = 2000000
    val row = (minX..maxX).map {x ->
        // Assume it's possible until it's not
        var returnValue = '.'

        for (sensor in sensors) {
            // There is literally a beacon in this place
            if ((sensor.beacon.x == x) and (sensor.beacon.y == y)) {
                returnValue = 'B'
                break
            }

            // Assuming that as there's a sensor here, there's not also a beacon?
            else if ((sensor.x == x) and (sensor.y == y)) {
                returnValue = 'S'
                break
            }

            // There can't be a beacon here if it's in coverage but isn't a previously known beacon
            else if (sensor.inCoverage(x, y)) {
                returnValue = '#'
                // Don't break in case there's a previously known beacon/sensor in this spot
            }
        }
        returnValue
    }

    val count = row.count { it == '#' }

    println("Part 1: $count")

    // Part 2

    doPart2(sensors)

}

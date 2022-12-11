import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainKtTest {
    @Test
    fun testSolve() {
        val (part1, part2) = solve("test.txt")

        // This should be in a fixture, really.
        val part2Expected = arrayOf("##..##..##..##..##..##..##..##..##..##..",
                                    "###...###...###...###...###...###...###.",
                                    "####....####....####....####....####....",
                                    "#####.....#####.....#####.....#####.....",
                                    "######......######......######......####",
                                    "#######.......#######.......#######.....").joinToString("\n")

        assertEquals(13140, part1)
        assertEquals(part2Expected, part2)
    }
}
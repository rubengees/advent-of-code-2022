import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    private val example = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()

    @Test
    fun part1Example() {
        assertEquals("24000", day1Part1(example))
    }

    @Test
    fun part1Input() {
        assertEquals("74711", day1Part1(readInputFile(1)))
    }

    @Test
    fun part2Example() {
        assertEquals("45000", day1Part2(example))
    }

    @Test
    fun part2Input() {
        assertEquals("209481", day1Part2(readInputFile(1)))
    }
}

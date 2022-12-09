import kotlin.test.Test
import kotlin.test.assertEquals

class Day4Test {
    private val example = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()

    @Test
    fun part1Example() {
        assertEquals("2", day4Part1(example))
    }

    @Test
    fun part1Input() {
        assertEquals("500", day4Part1(readInputFile(4)))
    }

    @Test
    fun part2Example() {
        assertEquals("4", day4Part2(example))
    }

    @Test
    fun part2Input() {
        assertEquals("815", day4Part2(readInputFile(4)))
    }
}

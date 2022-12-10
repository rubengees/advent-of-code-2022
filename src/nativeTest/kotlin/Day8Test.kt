import kotlin.test.Test
import kotlin.test.assertEquals

class Day8Test {
    private val example = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    @Test
    fun part1Example() {
        assertEquals("21", day8Part1(example))
    }

    @Test
    fun part1Input() {
        assertEquals("1829", day8Part1(readInputFile(8)))
    }

    @Test
    fun part2Example() {
        assertEquals("8", day8Part2(example))
    }

    @Test
    fun part2Input() {
        assertEquals("291840", day8Part2(readInputFile(8)))
    }
}
import kotlin.test.Test
import kotlin.test.assertEquals

class Day9Test {
    private val example = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    private val exampleLarge = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()

    @Test
    fun part1Example() {
        assertEquals("13", day9Part1(example))
    }

    @Test
    fun part1Input() {
        assertEquals("6271", day9Part1(readInputFile(9)))
    }

    @Test
    fun part2Example() {
        assertEquals("1", day9Part2(example))
    }

    @Test
    fun part2ExampleLarge() {
        assertEquals("36", day9Part2(exampleLarge))
    }

    @Test
    fun part2Input() {
        assertEquals("2458", day9Part2(readInputFile(9)))
    }
}

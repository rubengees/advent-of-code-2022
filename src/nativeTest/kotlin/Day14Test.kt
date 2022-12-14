import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {
    private val day = Day14()

    private val example = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()

    @Test
    fun part1Example() = runBlocking {
        assertEquals("24", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking {
        assertEquals("858", day.part1(readInputFile(14)))
    }

    @Test
    fun part2Example() = runBlocking {
        assertEquals("93", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking {
        assertEquals("26845", day.part2(readInputFile(14)))
    }
}

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day4Test {
    private val day = Day4()

    private val example = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()

    @Test
    fun part1Example() = runBlocking {
        assertEquals("2", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking {
        assertEquals("500", day.part1(readInputFile(4)))
    }

    @Test
    fun part2Example() = runBlocking {
        assertEquals("4", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking {
        assertEquals("815", day.part2(readInputFile(4)))
    }
}

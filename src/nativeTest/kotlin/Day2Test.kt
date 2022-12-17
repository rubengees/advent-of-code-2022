import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {
    private val day = Day2()

    private val example = """
        A Y
        B X
        C Z
    """.trimIndent()

    @Test
    fun part1Example() = runBlocking(workerDispatcher()) {
        assertEquals("15", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking(workerDispatcher()) {
        assertEquals("15337", day.part1(readInputFile(2)))
    }

    @Test
    fun part2Example() = runBlocking(workerDispatcher()) {
        assertEquals("12", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking(workerDispatcher()) {
        assertEquals("11696", day.part2(readInputFile(2)))
    }
}

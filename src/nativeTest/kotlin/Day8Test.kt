import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day8Test {
    private val day = Day8()

    private val example = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    @Test
    fun part1Example() = runBlocking(workerDispatcher()) {
        assertEquals("21", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking(workerDispatcher()) {
        assertEquals("1829", day.part1(readInputFile(8)))
    }

    @Test
    fun part2Example() = runBlocking(workerDispatcher()) {
        assertEquals("8", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking(workerDispatcher()) {
        assertEquals("291840", day.part2(readInputFile(8)))
    }
}

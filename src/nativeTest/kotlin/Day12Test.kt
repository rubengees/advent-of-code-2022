import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day = Day12()

    private val example = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent()

    @Test
    fun part1Example() = runBlocking(workerDispatcher()) {
        assertEquals("31", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking(workerDispatcher()) {
        assertEquals("528", day.part1(readInputFile(12)))
    }

    @Test
    fun part2Example() = runBlocking(workerDispatcher()) {
        assertEquals("29", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking(workerDispatcher()) {
        assertEquals("522", day.part2(readInputFile(12)))
    }
}

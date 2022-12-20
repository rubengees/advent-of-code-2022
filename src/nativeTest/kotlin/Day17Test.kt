import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {
    private val day = Day17()

    private val example = """
        >>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
    """.trimIndent()

    @Test
    fun part1Example() = runBlocking(workerDispatcher()) {
        assertEquals("3068", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking(workerDispatcher()) {
        assertEquals("3059", day.part1(readInputFile(17)))
    }

    @Test
    fun part2Example() = runBlocking(workerDispatcher()) {
        assertEquals("", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking(workerDispatcher()) {
        assertEquals("", day.part2(readInputFile(17)))
    }
}

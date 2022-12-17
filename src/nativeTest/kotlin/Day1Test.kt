import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    private val day = Day1()

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
    fun part1Example() = runBlocking(workerDispatcher()) {
        assertEquals("24000", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking(workerDispatcher()) {
        assertEquals("74711", day.part1(readInputFile(1)))
    }

    @Test
    fun part2Example() = runBlocking(workerDispatcher()) {
        assertEquals("45000", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking(workerDispatcher()) {
        assertEquals("209481", day.part2(readInputFile(1)))
    }
}

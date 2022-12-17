import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day = Day13()

    private val example = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()

    @Test
    fun part1Example() = runBlocking(workerDispatcher()) {
        assertEquals("13", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking(workerDispatcher()) {
        assertEquals("6623", day.part1(readInputFile(13)))
    }

    @Test
    fun part2Example() = runBlocking(workerDispatcher()) {
        assertEquals("140", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking(workerDispatcher()) {
        assertEquals("23049", day.part2(readInputFile(13)))
    }
}

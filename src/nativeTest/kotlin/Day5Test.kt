import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
    private val day = Day5()

    private val example = """
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
    """.trimIndent()

    @Test
    fun part1Example() = runBlocking(workerDispatcher()) {
        assertEquals("CMZ", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking(workerDispatcher()) {
        assertEquals("VGBBJCRMN", day.part1(readInputFile(5)))
    }

    @Test
    fun part2Example() = runBlocking(workerDispatcher()) {
        assertEquals("MCD", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking(workerDispatcher()) {
        assertEquals("LBBVJBRMH", day.part2(readInputFile(5)))
    }
}

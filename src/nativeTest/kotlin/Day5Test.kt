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
    fun part1Example() = runBlocking {
        assertEquals("CMZ", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking {
        assertEquals("VGBBJCRMN", day.part1(readInputFile(5)))
    }

    @Test
    fun part2Example() = runBlocking {
        assertEquals("MCD", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking {
        assertEquals("LBBVJBRMH", day.part2(readInputFile(5)))
    }
}

import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
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
    fun part1Example() {
        assertEquals("CMZ", day5Part1(example))
    }

    @Test
    fun part1Input() {
        assertEquals("VGBBJCRMN", day5Part1(readInputFile(5)))
    }

    @Test
    fun part2Example() {
        assertEquals("MCD", day5Part2(example))
    }

    @Test
    fun part2Input() {
        assertEquals("LBBVJBRMH", day5Part2(readInputFile(5)))
    }
}

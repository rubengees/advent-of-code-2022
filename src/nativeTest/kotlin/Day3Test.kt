import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {
    private val day = Day3()

    private val example = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()

    @Test
    fun part1Example() {
        assertEquals("157", day.part1(example))
    }

    @Test
    fun part1Input() {
        assertEquals("7850", day.part1(readInputFile(3)))
    }

    @Test
    fun part2Example() {
        assertEquals("70", day.part2(example))
    }

    @Test
    fun part2Input() {
        assertEquals("2581", day.part2(readInputFile(3)))
    }
}

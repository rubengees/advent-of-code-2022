import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {
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
        assertEquals("157", day3Part1(example))
    }

    @Test
    fun part1Input() {
        assertEquals("7850", day3Part1(readInputFile(3)))
    }

    @Test
    fun part2Example() {
        assertEquals("70", day3Part2(example))
    }

    @Test
    fun part2Input() {
        assertEquals("2581", day3Part2(readInputFile(3)))
    }
}

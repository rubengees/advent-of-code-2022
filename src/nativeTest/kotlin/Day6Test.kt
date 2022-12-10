import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
    private val examplesPart1 = mapOf(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to "7",
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to "5",
        "nppdvjthqldpwncqszvftbrmjlhg" to "6",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to "10",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to "11",
    )

    private val examplesPart2 = mapOf(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to "19",
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to "23",
        "nppdvjthqldpwncqszvftbrmjlhg" to "23",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to "29",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to "26",
    )

    @Test
    fun part1Example() {
        for ((input, expectedOutput) in examplesPart1) {
            assertEquals(expectedOutput, day6Part1(input))
        }
    }

    @Test
    fun part1Input() {
        assertEquals("1965", day6Part1(readInputFile(6)))
    }

    @Test
    fun part2Example() {
        for ((input, expectedOutput) in examplesPart2) {
            assertEquals(expectedOutput, day6Part2(input))
        }
    }

    @Test
    fun part2Input() {
        assertEquals("2773", day6Part2(readInputFile(6)))
    }
}

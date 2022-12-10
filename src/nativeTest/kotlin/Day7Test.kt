import kotlin.test.Test
import kotlin.test.assertEquals

class Day7Test {
    private val example = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()

    @Test
    fun part1Example() {
        assertEquals("95437", day7Part1(example))
    }

    @Test
    fun part1Input() {
        assertEquals("1915606", day7Part1(readInputFile(7)))
    }

    @Test
    fun part2Example() {
        assertEquals("24933642", day7Part2(example))
    }

    @Test
    fun part2Input() {
        assertEquals("5025657", day7Part2(readInputFile(7)))
    }
}

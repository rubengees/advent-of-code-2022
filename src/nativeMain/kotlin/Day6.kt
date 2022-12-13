class Day6 : Day {
    override suspend fun part1(input: String): String {
        val count = input.windowedSequence(4, 1).takeWhile { it.toSet().size != it.length }.count()

        return (count + 4).toString()
    }

    override suspend fun part2(input: String): String {
        val count = input.windowedSequence(14, 1).takeWhile { it.toSet().size != it.length }.count()

        return (count + 14).toString()
    }
}

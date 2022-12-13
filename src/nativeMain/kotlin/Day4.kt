class Day4 : Day {
    private fun parseLine(line: String): Pair<IntRange, IntRange> {
        val ranges = line.split(",")
            .map { range -> range.split("-").map { it.toInt() } }
            .map { IntRange(it[0], it[1]) }

        return ranges[0] to ranges[1]
    }

    override suspend fun part1(input: String): String {
        return input.lines()
            .map { parseLine(it) }
            .count { (a, b) -> a.intersect(b).size in listOf(a.toList().size, b.toList().size) }
            .toString()
    }

    override suspend fun part2(input: String): String {
        return input.lines()
            .map { parseLine(it) }
            .count { (a, b) -> a.intersect(b).isNotEmpty() }
            .toString()
    }
}

fun parseLine(line: String): Pair<IntRange, IntRange> {
    val ranges = line.split(",")
        .map { range -> range.split("-").map { it.toInt() } }
        .map { IntRange(it[0], it[1]) }

    return ranges[0] to ranges[1]
}

fun day4Part1(input: String): String {
    return input.lines()
        .map { parseLine(it) }
        .count { (a, b) -> a.intersect(b).size in listOf(a.toList().size, b.toList().size) }
        .toString()
}

fun day4Part2(input: String): String {
    return input.lines()
        .map { parseLine(it) }
        .count { (a, b) -> a.intersect(b).isNotEmpty() }
        .toString()
}

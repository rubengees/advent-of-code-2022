class Day3 : Day {
    private fun Char.priority(): Int {
        return when (this) {
            in ('a'..'z') -> code - 'a'.code + 1
            in ('A'..'Z') -> code - 'A'.code + 27
            else -> error("Unsupported char $this")
        }
    }

    private fun findDuplicate(a: List<Char>, b: List<Char>): Char? {
        return a.find { b.contains(it) }
    }

    override suspend fun part1(input: String): String {
        return input.lines()
            .map { it.toCharArray().toList() }
            .mapNotNull { findDuplicate(it.subList(0, it.size / 2), it.subList(it.size / 2, it.size)) }
            .sumOf { it.priority() }
            .toString()
    }

    private fun findDuplicate(a: List<Char>, vararg b: List<Char>): Char? {
        return a.find { item -> b.all { it.contains(item) } }
    }

    override suspend fun part2(input: String): String {
        return input.lines()
            .map { it.toCharArray().toList() }
            .chunked(3)
            .mapNotNull { findDuplicate(it[0], *it.subList(1, it.size).toTypedArray()) }
            .sumOf { it.priority() }
            .toString()
    }
}

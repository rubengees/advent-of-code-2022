class Day1 {
    fun part1(input: String): String {
        var current = 0
        var max = 0

        for (line in input.lines()) {
            if (line.isEmpty()) {
                current = 0
            } else {
                current += line.toInt()

                if (current >= max) {
                    max = current
                }
            }
        }

        return max.toString()
    }

    fun part2(input: String): String {
        val calories = input.lines().fold(mutableListOf(0)) { acc: List<Int>, next: String ->
            if (next.isEmpty()) {
                acc + 0
            } else {
                val last = acc.last()

                acc.dropLast(1).plus(last + next.toInt())
            }
        }

        return calories.sortedDescending().take(3).sum().toString()
    }
}

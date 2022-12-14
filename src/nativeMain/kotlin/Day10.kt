class Day10 : Day {
    private fun simulate(instructions: List<String>): Sequence<Int> {
        return sequence {
            var register = 1

            yield(register)

            for (instruction in instructions) {
                if (instruction == "noop") {
                    // Nothing to do.
                } else if (instruction.startsWith("addx")) {
                    yield(register)

                    register += instruction.substring(5).toInt()
                } else {
                    error("Unknown instruction")
                }

                yield(register)
            }
        }
    }

    override suspend fun part1(input: String): String {
        val simulated = simulate(input.lines()).toList()

        return listOf(20, 60, 100, 140, 180, 220).sumOf { simulated[it - 1] * it }.toString()
    }

    override suspend fun part2(input: String): String {
        return simulate(input.lines()).take(240).mapIndexed { cycle: Int, register: Int ->
            val position = cycle % 40

            buildString {
                if (cycle > 0 && position == 0) append("\n")

                if (position in register - 1..register + 1) {
                    append("#")
                } else {
                    append(".")
                }
            }
        }.joinToString(separator = "")
    }
}

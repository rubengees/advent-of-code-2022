class Day11 {
    private data class Monkey(
        val items: List<Long>,
        val operation: (Long) -> Long,
        val divisor: Long,
        val trueTarget: Int,
        val falseTarget: Int,
    ) {
        fun targetFor(value: Long): Int {
            return if (value.mod(divisor) == 0L) trueTarget else falseTarget
        }
    }

    private fun parse(input: String): List<Monkey> {
        val groups = input.split("\n\n")

        val monkeys = groups.map { group ->
            val items = group.lines()[1].substring(18).split(", ").map { it.toLong() }
            val operation = parseOperation(group.lines()[2])
            val divisor = group.lines()[3].substring(21).toLong()
            val trueTarget = group.lines()[4].substring(29).toInt()
            val falseTarget = group.lines()[5].substring(30).toInt()

            Monkey(items, operation, divisor, trueTarget, falseTarget)
        }

        return monkeys
    }

    private fun parseOperation(line: String): (Long) -> Long {
        val operationSubject = line.substring(25)

        val operator = when (line[23]) {
            '*' -> { a: Long, b: Long -> a * b }
            '+' -> { a: Long, b: Long -> a + b }
            else -> error("Unknown operator ${line[23]}")
        }

        return { operator(it, if (operationSubject == "old") it else operationSubject.toLong()) }
    }

    private fun simulate(monkeys: List<Monkey>, rounds: Int, relief: (Long) -> Long): List<Long> {
        val mutableMonkeys = monkeys.toMutableList()
        val inspections = mutableMapOf<Int, Long>()

        repeat(rounds) {
            mutableMonkeys.forEachIndexed { index, monkey ->
                inspections[index] = inspections.getOrElse(index) { 0 } + monkey.items.size

                for (item in monkey.items) {
                    val inspectedItem = relief(monkey.operation(item))
                    val nextMonkeyIndex = monkey.targetFor(inspectedItem)
                    val nextMonkey = mutableMonkeys[nextMonkeyIndex]

                    mutableMonkeys[nextMonkeyIndex] = nextMonkey.copy(items = nextMonkey.items + inspectedItem)
                    mutableMonkeys[index] = monkey.copy(items = emptyList())
                }
            }
        }

        return inspections.values.toList()
    }

    fun part1(input: String): String {
        val result = simulate(parse(input), rounds = 20) { it / 3 }

        return result.sortedDescending().take(2).reduce { acc, curr -> acc * curr }.toString()
    }

    fun part2(input: String): String {
        val monkeys = parse(input)
        val safeReliefModulo = monkeys.fold(1L) { acc, curr -> acc * curr.divisor }
        val result = simulate(parse(input), rounds = 10_000) { it.mod(safeReliefModulo) }

        return result.sortedDescending().take(2).reduce { acc, curr -> acc * curr }.toString()
    }
}

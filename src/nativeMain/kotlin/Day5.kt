data class Instruction(val times: Int, val from: Int, val to: Int)

fun parseInput(input: String): Pair<MutableList<ArrayDeque<Char>>, List<Instruction>> {
    val stackLines = input.lines().takeWhile { !it.startsWith(" 1 ") }

    val stackLineChars = stackLines.map { line ->
        line.toCharArray().toList()
            .chunked(4)
            .map { chunk -> chunk.find { char -> char.isLetter() } }
    }

    val emptyStacks = stackLineChars.last().map { ArrayDeque<Char>() }.toMutableList()

    val stacks = stackLineChars.reversed().fold(emptyStacks) { acc, chars: List<Char?> ->
        chars.forEachIndexed { index, char ->
            if (char != null) {
                acc[index].add(char)
            }
        }

        acc
    }


    val instructionLines = input.lines().drop(stackLines.size + 2)

    val instructions = instructionLines.map { line ->
        val match = Regex("move (\\d+) from (\\d+) to (\\d+)").matchEntire(line) ?: error("Invalid instruction")

        Instruction(match.groupValues[1].toInt(), match.groupValues[2].toInt(), match.groupValues[3].toInt())
    }

    return stacks to instructions
}

fun day5Part1(input: String): String {
    val (stacks, instructions) = parseInput(input)

    for (instruction in instructions) {
        repeat(instruction.times) {
            stacks[instruction.to - 1].add(stacks[instruction.from - 1].removeLast())
        }
    }

    return stacks.map { it.last() }.joinToString("")
}

fun day5Part2(input: String): String {
    val (stacks, instructions) = parseInput(input)

    for (instruction in instructions) {
        val movingElements = (0 until  instruction.times).fold(emptyList<Char>()) { acc, _ ->
            acc + stacks[instruction.from - 1].removeLast()
        }

        stacks[instruction.to - 1].addAll(movingElements.reversed())
    }

    return stacks.map { it.last() }.joinToString("")
}

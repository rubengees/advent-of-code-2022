private val winTable = mapOf(1 to 3, 2 to 1, 3 to 2)

private fun calculateScorePart1(left: Char, right: Char): Int {
    val leftCode = left.code - 'A'.code + 1
    val rightCode = right.code - 'X'.code + 1

    return when {
        winTable[rightCode] == leftCode -> 6 + rightCode
        winTable[leftCode] == rightCode -> rightCode
        else -> 3 + rightCode
    }
}

fun day2Part1(input: String): String {
    return input.lines()
        .sumOf {
            val chars = it.split(" ").map { part -> part.first() }

            calculateScorePart1(chars.first(), chars.last())
        }
        .toString()
}

private val reverseWinTable = winTable.entries.associateBy({ it.value }) { it.key }

private fun calculateScorePart2(left: Char, right: Char): Int {
    val leftCode = left.code - 'A'.code + 1

    return when (right) {
        'X' -> winTable.getValue(leftCode)
        'Y' -> 3 + leftCode
        'Z' -> 6 + reverseWinTable.getValue(leftCode)
        else -> error("Unknown right $right")
    }
}

fun day2Part2(input: String): String {
    return input.lines()
        .sumOf {
            val chars = it.split(" ").map { part -> part.first() }

            calculateScorePart2(chars.first(), chars.last())
        }
        .toString()
}

private fun arrayOfZeros(size: Int): Array<Int> {
    return arrayOf(*(0 until size).map { 0 }.toTypedArray())
}

private class Matrix(size: Int) {
    private val data: Array<Array<Int>> = (0 until size).map { arrayOfZeros(size) }.toTypedArray()

    operator fun set(x: Int, y: Int, value: Int) {
        this.data[x][y] = value
    }

    operator fun get(x: Int, y: Int): Int {
        return this.data[x][y]
    }

    fun isVisible(x: Int, y: Int): Boolean {
        val value = get(x, y)

        if ((0 until x).all { i -> value > get(i, y) }) return true // Check left edge.
        if ((x + 1 until data.size).all { i -> value > get(i, y) }) return true // Check right edge.
        if ((0 until y).all { i -> value > get(x, i) }) return true // Check top edge.
        if ((y + 1 until data.size).all { i -> value > get(x, i) }) return true // Check bottom edge.

        return false
    }

    fun scenicScore(x: Int, y: Int): Int {
        val value = get(x, y)

        val blockingLeft = (x - 1 downTo 0).find { i -> value <= get(i, y) }
        val blockingRight = (x + 1 until data.size).find { i -> value <= get(i, y) }
        val blockingTop = (y - 1 downTo 0).find { i -> value <= get(x, i) }
        val blockingBottom = (y + 1 until data.size).find { i -> value <= get(x, i) }

        val scoreLeft = x - (blockingLeft ?: 0)
        val scoreRight = (blockingRight ?: (data.size - 1)) - x
        val scoreTop = y - (blockingTop ?: 0)
        val scoreBottom = (blockingBottom ?: (data.size - 1)) - y

        return scoreLeft * scoreRight * scoreTop * scoreBottom
    }

    fun positions(): Sequence<Pair<Int, Int>> {
        return sequence {
            data.forEachIndexed { x, rows ->
                rows.forEachIndexed { y, _ -> yield(x to y) }
            }
        }
    }
}

private fun parse(input: String): Matrix {
    val result = Matrix(input.lines().first().length)

    input.lines().forEachIndexed { x, line ->
        line.forEachIndexed { y, char ->
            result[x, y] = char.digitToInt()
        }
    }

    return result
}

fun day8Part1(input: String): String {
    val matrix = parse(input)

    return matrix.positions().count { (x, y) -> matrix.isVisible(x, y) }.toString()
}

fun day8Part2(input: String): String {
    val matrix = parse(input)

    return matrix.positions().maxOf { (x, y) -> matrix.scenicScore(x, y) }.toString()
}

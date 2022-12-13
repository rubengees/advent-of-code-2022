import kotlin.math.abs

class Day9 : Day {
    private data class Point(val x: Int, val y: Int) {
        fun distanceTo(other: Point) = Distance(this.x - other.x, this.y - other.y)

        fun move(distance: Distance) = copy(x = x + distance.x, y = y + distance.y)

        fun moveBehind(other: Point): Point {
            val distance = other.distanceTo(this)

            return if (abs(distance.x) == 2 || abs(distance.y) == 2) {
                this.move(Distance(x = distance.x.coerceIn(-1..1), y = distance.y.coerceIn(-1..1)))
            } else {
                this
            }
        }
    }

    private data class Distance(val x: Int, val y: Int)

    private fun parseDirection(char: Char): Distance {
        return when (char) {
            'L' -> Distance(x = -1, y = 0)
            'R' -> Distance(x = 1, y = 0)
            'U' -> Distance(x = 0, y = -1)
            'D' -> Distance(x = 0, y = 1)
            else -> error("Unknown direction $char")
        }
    }

    override suspend fun part1(input: String): String {
        val visited = mutableSetOf(Point(0, 0))

        var head = Point(x = 0, y = 0)
        var tail = Point(x = 0, y = 0)

        for (line in input.lines()) {
            val directionDistance = parseDirection(line[0])
            val amount = line.substring(2).toInt()

            repeat(amount) {
                head = head.move(directionDistance)
                tail = tail.moveBehind(head)

                visited.add(tail)
            }
        }

        return visited.size.toString()
    }

    override suspend fun part2(input: String): String {
        val visited = mutableSetOf(Point(x = 0, y = 0))

        var head = Point(x = 0, y = 0)
        val tails = (0 until 9).map { Point(x = 0, y = 0) }.toMutableList()

        for (line in input.lines()) {
            val directionDistance = parseDirection(line[0])
            val amount = line.substring(2).toInt()

            repeat(amount) {
                head = head.move(directionDistance)

                for (i in 0 until tails.size) {
                    tails[i] = tails[i].moveBehind(tails.getOrElse(i - 1) { head })
                }

                visited.add(tails.last())
            }
        }

        return visited.size.toString()
    }
}

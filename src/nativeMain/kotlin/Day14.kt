import Day14.Material.ROCK
import Day14.Material.SAND

class Day14 : Day {

    private data class Point(val x: Int, val y: Int) {
        fun rangeTo(other: Point): List<Point> {
            return (x toward other.x).flatMap { x ->
                (y toward other.y).map { y -> Point(x, y) }
            }
        }

        private infix fun Int.toward(to: Int): IntProgression {
            val step = if (this > to) -1 else 1

            return IntProgression.fromClosedRange(this, to, step)
        }
    }

    private enum class Material { ROCK, SAND }

    private class Matrix(data: Map<Point, Material>, private val floor: Int? = null) {
        private val data = data.toMutableMap()

        private val voidStart = floor?.plus(1) ?: data.maxOf { (point) -> point.y + 1 }

        fun isBlocked(point: Point): Boolean {
            return point.y == floor || data[point] != null
        }

        fun addSand(): Boolean {
            var x = 500
            var y = 0

            while (y < voidStart) {
                if (!isBlocked(Point(x, y + 1))) {
                    y += 1
                } else if (!isBlocked(Point(x - 1, y + 1))) {
                    x -= 1
                    y += 1
                } else if (!isBlocked(Point(x + 1, y + 1))) {
                    x += 1
                    y += 1
                } else if (!isBlocked(Point(x, y))) {
                    data[Point(x, y)] = SAND

                    return true
                } else {
                    return false
                }
            }

            return false
        }

        fun withFloor(): Matrix {
            return Matrix(data, voidStart + 1)
        }
    }

    private fun parse(input: String): Matrix {
        val data = mutableMapOf<Point, Material>()

        for (line in input.lines()) {
            val points = line.split(" -> ").map { point ->
                val (x, y) = point.split(",").map { it.toInt() }

                Point(x, y)
            }

            val ranges = points.fold(emptyList<Point>()) { acc, point ->
                acc + (acc.lastOrNull()?.rangeTo(point) ?: listOf(point))
            }

            data.putAll(ranges.associateWith { ROCK })
        }

        return Matrix(data)
    }

    override suspend fun part1(input: String): String {
        val matrix = parse(input)

        return generateSequence { matrix.addSand() }.takeWhile { it }.count().toString()
    }

    override suspend fun part2(input: String): String {
        val matrix = parse(input).withFloor()

        return generateSequence { matrix.addSand() }.takeWhile { it }.count().toString()
    }
}

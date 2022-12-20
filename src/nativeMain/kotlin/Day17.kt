import kotlin.math.max

class Day17 : Day {
    private data class Point(val x: Int, val y: Int)

    private data class Shape(val data: Map<Point, Unit>) {
        fun moveTo(x: Int, y: Int): Shape {
            return Shape(data.mapKeys { (point, _) -> Point(point.x + x, point.y + y) })
        }

        fun collides(chamber: Map<Point, Unit>): Boolean {
            return data.keys.any { chamber.containsKey(it) || it.x < 0 || it.x > 6 }
        }

        fun collides(x: Int, y: Int, chamber: Map<Point, Unit>): Boolean {
            return moveTo(x, y).collides(chamber)
        }
    }

    private val shapes = listOf(
        Shape(
            mapOf(
                Point(0, 0) to Unit,
                Point(1, 0) to Unit,
                Point(2, 0) to Unit,
                Point(3, 0) to Unit
            )
        ),
        Shape(
            mapOf(
                Point(1, 0) to Unit,
                Point(0, 1) to Unit,
                Point(1, 1) to Unit,
                Point(2, 1) to Unit,
                Point(1, 2) to Unit
            )
        ),
        Shape(
            mapOf(
                Point(0, 0) to Unit,
                Point(1, 0) to Unit,
                Point(2, 0) to Unit,
                Point(2, 1) to Unit,
                Point(2, 2) to Unit
            )
        ),
        Shape(
            mapOf(
                Point(0, 0) to Unit,
                Point(0, 1) to Unit,
                Point(0, 2) to Unit,
                Point(0, 3) to Unit
            )
        ),
        Shape(
            mapOf(
                Point(0, 0) to Unit,
                Point(1, 0) to Unit,
                Point(0, 1) to Unit,
                Point(1, 1) to Unit
            )
        )
    )

    private fun parseJet(input: Char) = when (input) {
        '>' -> 1
        '<' -> -1
        else -> error("Invalid input $input")
    }

    private fun simulate(jets: List<Int>, times: Int): Map<Point, Unit> {
        val chamber = (0 until 7).associate { Point(it, 0) to Unit }.toMutableMap()
        val jetSequence = sequence { repeat(Int.MAX_VALUE) { yieldAll(jets) } }.iterator()
        var top = chamber.keys.maxOf { it.y }

        repeat(times) { step ->
            var shape = shapes[step.mod(shapes.size)].moveTo(2, top + 4)

            while (true) {
                val nextJet = jetSequence.next()

                if (!shape.collides(nextJet, 0, chamber)) {
                    shape = shape.moveTo(nextJet, 0)
                }

                if (!shape.collides(0, -1, chamber)) {
                    shape = shape.moveTo(0, -1)
                } else {
                    break
                }
            }

            chamber.putAll(shape.data)
            top = max(top, shape.data.keys.maxOf { it.y })
        }

        return chamber
    }

    override suspend fun part1(input: String): String {
        val jets = input.map(::parseJet)
        val chamber = simulate(jets, 2022)

        return chamber.keys.maxOf { it.y }.toString()
    }

    override suspend fun part2(input: String): String {
        return ""
    }
}

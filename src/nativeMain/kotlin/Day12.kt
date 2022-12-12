import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class Day12 {
    private companion object {
        private const val START = 'S'
        private const val END = 'E'
    }

    private data class Point(val x: Int, val y: Int)

    private class Matrix(private val data: List<List<Char>>) {
        val points get() = data.indices.flatMap { y -> data[y].indices.map { x -> Point(x, y) } }

        val start = points.find { get(it) == START } ?: error("Missing start")
        val end = points.find { get(it) == END } ?: error("Missing end")

        operator fun get(point: Point) = data[point.y][point.x]

        fun height(point: Point): Int {
            return when (val char = get(point)) {
                START -> 0
                END -> 25
                else -> char.code - 'a'.code
            }
        }

        fun neighbours(point: Point): List<Point> {
            val (x, y) = point

            return listOf(Point(x, y - 1), Point(x, y + 1), Point(x - 1, y), Point(x + 1, y))
                .filter { data.getOrNull(it.y)?.getOrNull(it.x) != null }
                .filter { height(point) >= height(it) - 1 }
        }

        fun withStartAt(point: Point): Matrix {
            val mutableData = data.map { it.toMutableList() }.toMutableList()

            mutableData[start.y][start.x] = 'a'
            mutableData[point.y][point.x] = 'S'

            return Matrix(mutableData)
        }
    }

    private fun parse(input: String): Matrix {
        val data = input.lines().map { line ->
            line.toCharArray().toList()
        }

        return Matrix(data)
    }

    private fun aStar(matrix: Matrix, h: (a: Point, b: Point) -> Int): List<Point>? {
        val openSet = mutableSetOf(matrix.start)
        val cameFrom = mutableMapOf<Point, Point>()
        val gScore = mutableMapOf(matrix.start to 0)

        fun reconstructPath(): List<Point> {
            val result = mutableListOf(matrix.end)
            var current = matrix.end

            while (current in cameFrom) {
                current = cameFrom.getValue(current)
                result.add(current)
            }

            return result
        }

        while (openSet.isNotEmpty()) {
            val current = openSet.minBy { point -> matrix[point] + h(point, matrix.end) }

            if (current == matrix.end) {
                return reconstructPath()
            }

            openSet.remove(current)

            for (neighbour in matrix.neighbours(current)) {
                val score = gScore.getValue(current) + 1

                if (score < gScore.getOrElse(neighbour) { Int.MAX_VALUE }) {
                    cameFrom[neighbour] = current
                    gScore[neighbour] = score

                    openSet.add(neighbour)
                }
            }
        }

        return null
    }

    private suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B) = coroutineScope {
        map { async { f(it) } }.awaitAll()
    }

    fun part1(input: String): String {
        val matrix = parse(input)
        val heuristic = { a: Point, b: Point -> matrix.height(b) - matrix.height(a) }
        val shortestPath = aStar(matrix, heuristic) ?: error("No path found")

        return (shortestPath.count() - 1).toString()
    }

    suspend fun part2(input: String): String {
        val matrix = parse(input)
        val heuristic = { a: Point, b: Point -> matrix.height(b) - matrix.height(a) }

        val shortestPath = matrix.points
            .filter { matrix.height(it) == 0 }
            .pmap { aStar(matrix.withStartAt(it), heuristic) }
            .filterNotNull()
            .minBy { it.count() }

        return (shortestPath.count() - 1).toString()
    }
}

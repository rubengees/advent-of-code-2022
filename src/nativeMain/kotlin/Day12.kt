class Day12 : Day {
    private companion object {
        private const val START = 'S'
        private const val END = 'E'
    }

    private data class Point(val x: Int, val y: Int)

    private class Matrix(private val data: List<List<Char>>) {
        private val points get() = data.indices.flatMap { y -> data[y].indices.map { x -> Point(x, y) } }

        val start = points.filter { get(it) == START }
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

        fun withAAsStart(): Matrix {
            return Matrix(data.map { lines -> lines.map { char -> if (char == 'a') START else char } })
        }
    }

    private class PriorityQueue<T>(private val comparator: Comparator<T>, data: Iterable<T> = emptyList()) {
        private val data = data.sortedWith(comparator).toMutableList()

        fun enqueue(item: T) {
            var index = data.binarySearch(item, comparator)
            if (index < 0) index = -index - 1
            data.add(index, item)
        }

        fun dequeue(): T? {
            return data.removeFirst()
        }

        fun isNotEmpty() = data.isNotEmpty()
    }

    private fun parse(input: String): Matrix {
        val data = input.lines().map { line ->
            line.toCharArray().toList()
        }

        return Matrix(data)
    }

    private fun aStar(matrix: Matrix, h: (a: Point, b: Point) -> Int): List<Point>? {
        val openSet = PriorityQueue(compareBy { matrix[it] + h(it, matrix.end) }, matrix.start)
        val cameFrom = mutableMapOf<Point, Point>()
        val gScore = matrix.start.associateWith { 0 }.toMutableMap()

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
            val current = openSet.dequeue() ?: error("openSet is empty")

            if (current == matrix.end) {
                return reconstructPath()
            }

            for (neighbour in matrix.neighbours(current)) {
                val score = gScore.getValue(current) + 1

                if (score < gScore.getOrElse(neighbour) { Int.MAX_VALUE }) {
                    cameFrom[neighbour] = current
                    gScore[neighbour] = score

                    openSet.enqueue(neighbour)
                }
            }
        }

        return null
    }

    override suspend fun part1(input: String): String {
        val matrix = parse(input)
        val heuristic = { _: Point, _: Point -> 0 }
        val shortestPath = aStar(matrix, heuristic) ?: error("No path found")

        return (shortestPath.count() - 1).toString()
    }

    override suspend fun part2(input: String): String {
        val matrix = parse(input)
        val heuristic = { _: Point, _: Point -> 0 }
        val shortestPath = aStar(matrix.withAAsStart(), heuristic) ?: error("No path found")

        return (shortestPath.count() - 1).toString()
    }
}

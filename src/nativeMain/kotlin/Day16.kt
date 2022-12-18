import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class Day16 : Day {
    private data class Path(val target: String, val cost: Int)
    private data class Valve(val flow: Int, val targets: List<Path>)

    private fun parse(input: String): Map<String, Valve> {
        val regex = Regex("""Valve (.+) has flow rate=(\d+); tunnels? leads? to valves? (.+)""")

        return input.lines()
            .associate { line ->
                val match = regex.matchEntire(line) ?: error("Regex does not match")
                val name = match.groupValues[1]
                val flow = match.groupValues[2].toInt()
                val targets = match.groupValues[3].split(", ")

                name to Valve(flow, targets.map { Path(it, 1) })
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

    private fun aStar(valves: Map<String, Valve>, start: String, end: String): List<String>? {
        val cameFrom = mutableMapOf<String, String>()
        val gScore = mutableMapOf(start to 0)
        val openSet = PriorityQueue(compareBy { gScore.getValue(it) }, listOf(start))

        fun reconstructPath(): List<String> {
            val result = mutableListOf(end)
            var current = end

            while (current in cameFrom) {
                current = cameFrom.getValue(current)
                result.add(current)
            }

            return result
        }

        while (openSet.isNotEmpty()) {
            val current = openSet.dequeue() ?: error("openSet is empty")

            if (current == end) {
                return reconstructPath()
            }

            for (neighbour in valves.getValue(current).targets) {
                val score = gScore.getValue(current) + neighbour.cost

                if (score < gScore.getOrElse(neighbour.target) { Int.MAX_VALUE }) {
                    cameFrom[neighbour.target] = current
                    gScore[neighbour.target] = score

                    openSet.enqueue(neighbour.target)
                }
            }
        }

        return null
    }

    private suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
        map { async { f(it) } }.awaitAll()
    }

    private suspend fun optimize(valves: Map<String, Valve>, start: String): Map<String, Valve> {
        val flowingValves = valves.filterValues { valve -> valve.flow >= 1 }

        val mapValve = { name: String, valve: Valve ->
            val targets = flowingValves.keys
                .filter { it != name }
                .map {
                    val cost = aStar(valves, name, it)?.size?.minus(1)

                    Path(it, cost ?: error("No path found from $name to $it"))
                }

            name to Valve(valve.flow, targets)
        }

        return coroutineScope {
            flowingValves.entries
                .pmap { (name, valve) -> mapValve(name, valve) }
                .plus(mapValve(start, valves.getValue(start)))
                .toMap()
        }
    }

    private fun simulate(
        valves: Map<String, Valve>,
        current: String,
        open: Set<String>,
        minutes: Int
    ): Int {
        if (minutes <= 0) return 0

        val results = mutableListOf<Int>()
        val currentValve = valves.getValue(current)
        val flow = currentValve.flow * (minutes - 1)

        val ventOpenCost = if (currentValve.flow >= 1) 1 else 0

        results += currentValve.targets
            .filter { !open.contains(it.target) }
            .map { simulate(valves, it.target, open + current, minutes - it.cost - ventOpenCost) }

        return results.maxOfOrNull { it + flow } ?: flow
    }

    override suspend fun part1(input: String): String {
        val valves = optimize(parse(input), "AA")

        return simulate(valves, "AA", emptySet(), 30).toString()
    }

    override suspend fun part2(input: String): String {
        return ""
    }
}

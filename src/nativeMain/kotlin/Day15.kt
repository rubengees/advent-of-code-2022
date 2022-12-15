import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day15 : Day {
    private data class Point(val x: Int, val y: Int) {
        fun distanceTo(other: Point): Int {
            return abs(x - other.x) + abs(y - other.y)
        }
    }

    private data class Sensor(val position: Point, val beacon: Point) {
        fun blockedPositionsAt(y: Int): IntRange {
            val distance = position.distanceTo(beacon)
            val positionDistance = abs(position.y - y)

            return (position.x - distance + positionDistance)..(position.x + distance - positionDistance)
        }
    }

    private fun parse(input: String): List<Sensor> {
        val regex = Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)")

        return input.lines().map { line ->
            val match = regex.matchEntire(line) ?: error("Invalid line $line")

            Sensor(
                Point(match.groupValues[1].toInt(), match.groupValues[2].toInt()),
                Point(match.groupValues[3].toInt(), match.groupValues[4].toInt())
            )
        }
    }

    private val IntRange.size get() = ((last - first) / step) + 1

    private fun IntRange.fastSubtract(other: IntRange): List<IntRange> {
        if (step != 1 || other.step != 1) {
            error("Only supported for ranges with step 1")
        }

        val result = if (other.first > first && other.last < last) {
            listOf(first until other.first, other.last + 1..last)
        } else if (other.first > first && other.last >= last) {
            listOf(first..min(other.first - 1, last))
        } else if (other.first <= first && other.last < last) {
            listOf(max(other.last + 1, first)..last)
        } else {
            emptyList()
        }

        return result
    }

    private fun blockedPositionsAtRow(sensors: List<Sensor>, row: Int): List<IntRange> {
        val ranges = sensors.map { it.blockedPositionsAt(row) }.filter { it.size >= 1 }

        return ranges.fold(emptyList()) { acc, range ->
            acc + (acc.fold(listOf(range)) { innerAcc, intRange ->
                innerAcc.flatMap { it.fastSubtract(intRange) }
            })
        }
    }

    private fun findGap(ranges: List<IntRange>): Int? {
        return ranges
            .sortedBy { it.first }
            .zipWithNext()
            .find { (a, b) -> b.first - a.last >= 2 }
            ?.first?.last?.plus(1)
    }

    override suspend fun part1(input: String): String {
        val sensors = parse(input)
        val row = if (sensors.maxOf { it.position.y } <= 100) 10 else 2_000_000

        val blockedPositionsAtRow = blockedPositionsAtRow(sensors, row)
        val beaconsAtRow = sensors.filter { it.beacon.y == row }.map { it.beacon.x }.toSet()

        return (blockedPositionsAtRow.sumOf { it.size } - beaconsAtRow.size).toString()
    }

    override suspend fun part2(input: String): String {
        val sensors = parse(input)
        val yBound = if (sensors.maxOf { it.position.y } <= 100) 20 else 4_000_000

        val result = (yBound downTo 0).asSequence()
            .map { y -> findGap(blockedPositionsAtRow(sensors, y))?.let { gap -> Point(gap, y) } }
            .find { it != null }

        if (result == null) {
            error("No gap found")
        } else {
            return (result.x * 4_000_000L + result.y).toString()
        }
    }
}

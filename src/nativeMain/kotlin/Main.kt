import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun main() {
    println("\uD83C\uDF84 Advent of Code 2022 \uD83C\uDF84")
    println()

    var overallDuration = Duration.ZERO
    var solved = 0

    for ((day, impl) in dayMapping) {
        val input = readInputFile(day)

        val (resultPart1, durationPart1) = runAndMeasure { impl.part1(input) }

        if (resultPart1.isNotEmpty()) {
            overallDuration += durationPart1
            solved += 1

            println("Day $day part 1 (${durationPart1.format()})")
            println(resultPart1)
        }

        val (resultPart2, durationPart2) = runAndMeasure { impl.part2(input) }

        if (resultPart2.isNotEmpty()) {
            overallDuration += durationPart2
            solved += 1

            println("Day $day part 2 (${durationPart2.format()})")
            println(resultPart2)
        }
    }

    println()
    println("\uD83C\uDF85 [$solved⭐] Christmas has been saved (${overallDuration.format()}) \uD83C\uDF85")
}

@OptIn(ExperimentalTime::class)
private fun runAndMeasure(block: suspend () -> String): Pair<String, Duration> {
    return runBlocking(workerDispatcher()) {
        val result: String

        val time = measureTime {
            result = block()
        }

        result to time
    }
}

private fun Duration.format(): String {
    return toString(DurationUnit.MILLISECONDS, 3)
}

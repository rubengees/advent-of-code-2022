import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {
    private val day = Day16()

    private val example = """
        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        Valve BB has flow rate=13; tunnels lead to valves CC, AA
        Valve CC has flow rate=2; tunnels lead to valves DD, BB
        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
        Valve EE has flow rate=3; tunnels lead to valves FF, DD
        Valve FF has flow rate=0; tunnels lead to valves EE, GG
        Valve GG has flow rate=0; tunnels lead to valves FF, HH
        Valve HH has flow rate=22; tunnel leads to valve GG
        Valve II has flow rate=0; tunnels lead to valves AA, JJ
        Valve JJ has flow rate=21; tunnel leads to valve II
    """.trimIndent()

    @Test
    fun part1Example() = runBlocking(workerDispatcher()) {
        assertEquals("1651", day.part1(example))
    }

    @Test
    fun part1Input() = runBlocking(workerDispatcher()) {
        assertEquals("1857", day.part1(readInputFile(16)))
    }

    @Test
    fun part2Example() = runBlocking(workerDispatcher()) {
        assertEquals("", day.part2(example))
    }

    @Test
    fun part2Input() = runBlocking(workerDispatcher()) {
        assertEquals("", day.part2(readInputFile(16)))
    }
}

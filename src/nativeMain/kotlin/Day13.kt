import Day13.Tree.Node
import Day13.Tree.Num
import kotlin.math.max

class Day13 : Day {
    private sealed class Tree {
        class Num(val value: Int) : Tree()

        class Node(vararg val items: Tree) : Tree(), Comparable<Node> {
            override fun compareTo(other: Node): Int {
                val otherNumbers = other.items

                for (i in 0 until max(items.size, otherNumbers.size)) {
                    val thisItem = items.getOrNull(i)
                    val otherItem = otherNumbers.getOrNull(i)

                    if (thisItem == null) return -1
                    if (otherItem == null) return 1

                    if (thisItem is Num && otherItem is Num) {
                        if (thisItem.value < otherItem.value) return -1
                        if (thisItem.value > otherItem.value) return 1
                    } else {
                        val thisItemList = if (thisItem is Num) Node(Num(thisItem.value)) else thisItem as Node
                        val otherItemList = if (otherItem is Num) Node(Num(otherItem.value)) else otherItem as Node

                        val result = thisItemList.compareTo(otherItemList)

                        if (result != 0) return result
                    }
                }

                return 0
            }
        }
    }

    private fun parseLineRecursive(line: String): Pair<Node, String> {
        var current = line.substring(1)
        val items = mutableListOf<Tree>()

        while (current.first() != ']') {
            val char = current.first()

            if (char.isDigit()) {
                val number = current.substringBefore(",").substringBefore("]")

                items.add(Num(number.toInt()))
                current = current.substring(number.length)
            } else if (char == '[') {
                val (newItem, remainder) = parseLineRecursive(current)

                items.add(newItem)
                current = remainder
            } else if (char == ',') {
                current = current.substring(1)
            } else {
                error("Illegal char $char")
            }
        }

        return Node(*items.toTypedArray()) to current.substring(1)
    }

    private fun parseLine(line: String): Node {
        return parseLineRecursive(line).first
    }

    private fun parse(input: String): List<Pair<Node, Node>> {
        val groups = input.split("\n\n")

        return groups.map { group ->
            val left = group.lines()[0]
            val right = group.lines()[1]

            parseLine(left) to parseLine(right)
        }
    }

    override suspend fun part1(input: String): String {
        val trees = parse(input)

        return trees
            .mapIndexedNotNull { index, (left, right) -> if (left < right) index + 1 else null }
            .sum()
            .toString()
    }

    override suspend fun part2(input: String): String {
        val trees = parse(input).flatMap { (a, b) -> listOf(a, b) }

        val dividerTwo = Node(Node(Num(2)))
        val dividerSix = Node(Node(Num(6)))
        val sortedTrees = (trees + dividerTwo + dividerSix).sorted()

        return ((sortedTrees.indexOf(dividerTwo) + 1) * (sortedTrees.indexOf(dividerSix) + 1)).toString()
    }
}

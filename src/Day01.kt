import kotlin.math.abs

fun main() {
    fun part1(leftList: MutableList<Int>, rightList: MutableList<Int>): Int {

        leftList.sort()
        rightList.sort()

        return leftList.foldIndexed(0) { index, acc, current ->
            acc + abs((abs(current) - abs(rightList[index])))
        }
    }

    fun part2(leftList: MutableList<Int>, rightList: MutableList<Int>): Int {
        val rightMap = hashMapOf<Int, Int>()

        rightList.map { element ->
            if (!rightMap.containsKey(element)) {
                rightMap[element] = rightList.filter { it == element }.size
            }
        }

        return leftList.fold(0) { acc, current ->
            acc + (current * (rightMap[current] ?: 0))
        }
    }

    // Read the input from the `src/inputs/Day01.txt` file.
    val input = readInput("Day01")

    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()

    input.forEach { pair ->
        val splitted = pair.split("   ")
        leftList.add(splitted.first().toInt())
        rightList.add(splitted.last().toInt())
    }

    println(part1(leftList, rightList))
    println(part2(leftList, rightList))
}

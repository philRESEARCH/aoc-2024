import kotlin.math.abs

fun main() {
    fun part1(reports: List<List<Long>>): Int {
        val safe = reports.mapNotNull { report ->
            var increase = true
            var isSafe = true

            report.forEachIndexed { i, level ->
                if (i == 0) {
                    val firstSmaller = report.indexOfFirst { it < level }
                    val firstLarger = report.indexOfFirst { it > level }
                    if (firstSmaller == -1) increase = true
                    else if (firstLarger == -1) increase = false
                    else if (firstLarger < firstSmaller) increase = true
                } else {
                    val previous = report[i-1]
                    val difference = abs(previous - level)

                    if (increase) {
                        if (previous > level || difference < 1 || difference > 3) isSafe = false
                    } else {
                        if (previous < level || difference < 1 || difference > 3) isSafe = false
                    }
                }
            }

            if (isSafe) report else null
        }

        return safe.size
    }

    fun isValid(list: List<Long>, checkIncreasing: Boolean): Boolean {
        for (i in 1 until list.size) {
            val diff = abs(list[i] - list[i - 1])
            if (diff !in 1..3) {
                return false
            }

            if (checkIncreasing && list[i] < list[i - 1]) {
                return false
            }
            if (!checkIncreasing && list[i] > list[i - 1]) {
                return false
            }
        }
        return true
    }

    fun part2(reports: List<List<Long>>): Int {
        val safe = reports.mapNotNull { report ->
            if (isValid(report, true) || isValid(report, false)) {
                report
            } else {
                var modifiedFound = false

                for (i in report.indices) {
                    val modified = report.toMutableList().apply { removeAt(i) }
                    if (isValid(modified, true) || isValid(modified, false)) modifiedFound = true
                }

                if (modifiedFound) report else null
            }
        }

        return safe.size
    }

    val input = readInput("Day02")

    val reports = input.map { line ->
        line.split(" ").map(String::toLong)
    }

    println(part1(reports))
    println(part2(reports))
}

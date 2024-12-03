fun main() {
    fun mulsToPair(muls: List<String>): List<Pair<Long, Long>> {
        return muls.map { mul ->
            val pairOfString = mul.substring(4, mul.length - 1).split(",")
            pairOfString[0].toLong() to pairOfString[1].toLong()
        }
    }

    fun foldResult(muls: List<String>): Long {
        val mulPairs = mulsToPair(muls)
        return mulPairs.fold(0L) { acc, current ->
            acc + (current.first * current.second)
        }
    }

    val input = readInputAsSingleLine("Day03").replace("\n", "")
    val mulRegex = "mul\\([1-9][0-9]{0,2},[1-9][0-9]{0,2}\\)".toRegex()

    /**
     * Part 1 Solution
     *
     * Pretty straight forward, use the above mulRegex to find all mul() occurrences
     */
    foldResult(mulRegex.findAll(input, 0).map { it.value }.toList()).println()

    /**
     * Part 2 Solution
     * The basic idea is that I find all substrings that start with do() and end on don't() (or end of input)
     * For these substrings, I can then go ahead and use the basic mulRegex so get all valid 'mul()'
     * Lastly, I needed to get all muls until the first occurrence of do() or don't() and consider these
     */
    val doRegex = "(do\\(\\).*?(don't\\(\\)))|(do\\(\\).*(?!don't\\(\\)))".toRegex()
    val doResult = doRegex.findAll(input, 0).map { it.value }
    val mulsFromDo = doResult.flatMap { mulRegex.findAll(it, 0).map { found -> found.value } }.toMutableList()

    val untilFirstDoOrDontRegex = "(.*?(do\\(\\)|don't\\(\\)))".toRegex()
    untilFirstDoOrDontRegex.find(input, 0)?.value?.let {
        val firstMuls = mulRegex.findAll(it, 0).map { mul -> mul.value }
        mulsFromDo.addAll(firstMuls)
    }

    foldResult(mulsFromDo).println()

}

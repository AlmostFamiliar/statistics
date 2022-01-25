import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

fun calc(table: List<List<Int>>) {
    val actual = calcRandhaeufigkeiten(table)
    printTable("ACTUAL", actual)

    val expected = calcExpected(actual)
    printTable("EXPECTED", expected)

    val msgFormat = "%-15s%s"
    if (table.size == 2 && table[0].size == 2) {
        val oddsRatio = oddsRatio(actual)
        println(msgFormat.format("Odds Ratio:", "$oddsRatio (Gegen 1 heißt unabhängig, weiter weg heißt abhängig!)"))
    } else {
        println(msgFormat.format("Odds Ratio:", "Table is not of size 2. Can not be calculated."))
    }

    val chi2 = calcChi2(actual, expected)
    println(msgFormat.format("Chi^2:", chi2))

    val cramer = calcCramer(chi2, expected)
    println(msgFormat.format("Cramer:", cramer))

    val pearson = calcPearson(chi2, expected)
    println(msgFormat.format("Pearson:", pearson))


    val pearsonMax = calcPearsonMax(expected)

    val pearsonKorr = pearson.divide(pearsonMax, 12, RoundingMode.HALF_UP)
    println(msgFormat.format("PearsonKorr:", pearsonKorr))
    println(msgFormat.format("PearsonMax:", pearsonMax))

    println("===============================================")
}

fun calcPearsonMax(expected: List<List<BigDecimal>>): BigDecimal {
    val min = BigDecimal(Math.min(expected.size - 1, expected[0].size - 1))

    return min.minus(BigDecimal.ONE).divide(min, 12, RoundingMode.HALF_UP).sqrt(MathContext(12))
}

fun calcPearson(chi2: BigDecimal, expected: List<List<BigDecimal>>): BigDecimal {
    val n = expected[expected.size - 1][expected[0].size - 1]
    val nenner = chi2.plus(n)
    val bruch = chi2.divide(nenner, 12, RoundingMode.HALF_UP)
    return bruch.sqrt(MathContext(12))
}

fun calcCramer(chi2: BigDecimal, expected: List<List<BigDecimal>>): BigDecimal {
    val n = expected[expected.size - 1][expected[0].size - 1]
    val min = Math.min(expected.size - 1, expected[0].size - 1) - 1
    val nenner = n.multiply(BigDecimal(min))
    val bruch = chi2.divide(nenner, 12, RoundingMode.HALF_UP)
    return bruch.sqrt(MathContext(12))
}

private fun calcRandhaeufigkeiten(
    table: List<List<Int>>,
): List<List<Int>> {
    val completeTable = mutableListOf<MutableList<Int>>()
    for (i in table.indices) {
        completeTable.add(mutableListOf())
        val row = completeTable[i]
        var fj = 0
        for (j in table[i].indices) {
            val rowValue = table[i][j]
            row.add(rowValue)
            fj += rowValue
        }
        row.add(fj)
    }

    val lastRow = mutableListOf<Int>()

    for (j in table[0].indices) {
        var columnSum = 0
        for (i in table.indices) {
            columnSum += table[i][j]
        }
        lastRow.add(columnSum)
    }
    val n = lastRow.sum()
    lastRow.add(n)
    completeTable.add(lastRow)

    return completeTable
}

fun calcChi2(actual: List<List<Int>>, expected: List<List<BigDecimal>>): BigDecimal {
    var sum = BigDecimal.ZERO.setScale(12)
    for (i in actual.indices) {
        for (j in expected.indices) {
            val difference = BigDecimal(actual[i][j]).minus(expected[i][j])
            sum += difference.multiply(difference).divide(expected[i][j], 12, RoundingMode.HALF_UP)
        }
    }
    return sum
}

fun oddsRatio(table: List<List<Int>>): BigDecimal {
    val ad = BigDecimal(table[0][0]).multiply(BigDecimal(table[1][1])).setScale(10)
    val bc = BigDecimal(table[1][0]).multiply(BigDecimal(table[0][1])).setScale(10)
    return ad.divide(bc, BigDecimal.ROUND_HALF_UP)
}

fun calcExpected(table: List<List<Int>>): List<List<BigDecimal>> {
    val expectedTable = copyList(table)
    for (i in 0 until table.size - 1) {
        for (j in 0 until table[0].size - 1) {
            val fj = table[i][table[0].size - 1]
            val fk = table[table.size - 1][j]
            val n = BigDecimal(table[table.size - 1][table[0].size - 1])
            expectedTable[i][j] = BigDecimal(fj * fk).divide(n, RoundingMode.HALF_UP)
        }
    }
    return expectedTable
}

fun copyList(table: List<List<Int>>): MutableList<MutableList<BigDecimal>> {
    val copy = mutableListOf<MutableList<BigDecimal>>()
    for (list in table) {
        copy.add(list.map { BigDecimal(it) }.toMutableList())
    }
    return copy;
}

fun expected(fk: Int, fj: Int, n: Int): Int {
    return fk * fj / n
}

fun printTable(title: String, table: List<List<Any>>) {

    println(title)

    println("______________________________________")
    table.forEach {
        var line = "| "
        it.forEach { el ->
            line += "%5s | ".format(el)
        }
        println(line)
    }
    println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾")


    println()
}
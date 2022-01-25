import java.math.BigDecimal
import java.math.RoundingMode

fun calc(table: List<List<Int>>) {
    val f1k = table[0][0] + table[1][0]
    val f2k = table[0][1] + table[1][1]
    val fj1 = table[0][0] + table[0][1]
    val fj2 = table[1][0] + table[1][1]
    val n = f1k + f2k
    assert(n == fj1 + fj2)

    val actual = calcRandhaeufigkeiten(table)
    printTable("ACTUAL", actual)

    val expected = calcExpected(actual)
    printTable("EXPECTED", expected)

    val msgFormat = "%-25s%s"
    if (table.size == 2 && table[0].size == 2) {
        val oddsRatio = oddsRatio(actual)
        println(msgFormat.format("Odds Ratio:", oddsRatio.toString()))
    } else {
        println(msgFormat.format("Odds Ratio:", "Table is not of size 2. Can not be calculated."))
    }

    // val chi2 = calcChi2(actual, expected)

    // println(msgFormat.format("X^2:", chi2.toString()))
    print("(Gegen 1 heißt unabhängig, weiter weg heißt abhängig!)\n")
    println("===============================================")
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

fun calcChi2(actual: List<List<Int>>, expected: List<List<Int>>): BigDecimal {
    var sum = BigDecimal.ZERO.setScale(12)
    for (i in actual.indices) {
        for (j in expected.indices) {
            val difference = actual[i][j] - expected[i][j]
            sum += BigDecimal(difference * difference).divide(BigDecimal(expected[i][j]), 12, RoundingMode.HALF_UP)
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
    for (i in 0 until table.size -1 ) {
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
        it.forEach{ el ->
            line += "%5s | ".format(el)
        }
        println(line)
    }
    println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾")


    println()
}
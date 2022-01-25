import java.math.BigDecimal
import java.math.BigDecimal.ROUND_HALF_UP
import java.math.RoundingMode

const val SCALE = 12
const val ROUNDING_MODE = BigDecimal.ROUND_HALF_DOWN

fun analyze(numbers: List<BigDecimal>) {
    val sortedList = numbers.sorted()
    val arithmetischesMittel = arithmetischesMittel(numbers)
    val varianz = varianz(numbers, arithmetischesMittel)

    println("====================================================================================")
    val msgFormat = "%-25s%s"
    println(msgFormat.format("Analyze:", numbers.toString()))
    println(msgFormat.format("Sorted:", sortedList.toString()))
    println(msgFormat.format("Arithmetisches Mittel:", arithmetischesMittel.toString()))
    println(msgFormat.format("Geometrisches Mittel", geometischesMittel(sortedList).toPlainString()))
    println(msgFormat.format("Modus:", modus(sortedList).toString()))
    println(msgFormat.format("Q1, Median, Q3", quartileUndMedian(sortedList).toString()))
    println(msgFormat.format("Varianz:", varianz.toString()))
    println(msgFormat.format("StandardAbweichung:", varianz.sqrt(SCALE).toString()))
    println("====================================================================================")
}

fun arithmetischesMittel(numbers: List<BigDecimal>): BigDecimal {
    var sum: BigDecimal = BigDecimal.ZERO.setScale(SCALE)
    for (number in numbers) {
        sum = sum.plus(number)
    }
    return sum.divide(BigDecimal(numbers.size), 12, RoundingMode.HALF_UP)
}

fun geometischesMittel(numbers: List<BigDecimal>): BigDecimal {
    var product: BigDecimal = numbers[0]
    for (i in 1 until numbers.size) {
        product = product.multiply(numbers[i])
    }
    // return BigDecimal(Math.pow(product.toDouble(), (1.0 / numbers.size)))

    return nthRoot(numbers.size, product)
}

fun modus(numbers: List<BigDecimal>): MutableSet<BigDecimal> {
    val numberToCount = mutableMapOf<BigDecimal, Int>()
    var highestCount = 0
    for (number in numbers) {
        val currentCount: Int = numberToCount[number] ?: 0
        val newCount = currentCount + 1
        numberToCount[number] = newCount

        if (newCount > highestCount) {
            highestCount = newCount
        }
    }

    val resultSet = mutableSetOf<BigDecimal>()
    for (entry in numberToCount) {
        if (entry.value == highestCount) {
            resultSet.add(entry.key)
        }
    }
    return resultSet
}

fun quartileUndMedian(numbers: List<BigDecimal>): List<BigDecimal> {
    val median = median(numbers)

    val lowerHalf = numbers.subList(0, numbers.size / 2)
    val q1 = median(lowerHalf)

    val upperHalf = numbers.subList(numbers.size / 2, numbers.size)
    val q3 = median(upperHalf)

    return listOf(q1, median, q3)
}

fun median(numbers: List<BigDecimal>): BigDecimal {
    val testSetSize = numbers.size

    val median = if (testSetSize % 2 == 0) {
        val lower = numbers[(testSetSize - 1) / 2]
        val upper = numbers[(testSetSize - 1) / 2 + 1]
        lower.plus(upper).divide(BigDecimal(2), 12, RoundingMode.HALF_UP)
    } else {
        numbers[testSetSize / 2]
    }
    return median
}

fun varianz(numbers: List<BigDecimal>, arithmetischesMittel: BigDecimal): BigDecimal {
    var zaehler = BigDecimal.ZERO.setScale(SCALE)
    for (number in numbers) {
        val bracket = number - arithmetischesMittel
        val squared = bracket.multiply(bracket)
        zaehler = zaehler.plus(squared)
    }
    return zaehler.div(BigDecimal(numbers.size))
}

fun nthRoot(n: Int, a: BigDecimal): BigDecimal {
    return nthRoot(n, a, BigDecimal.valueOf(.1).movePointLeft(SCALE))
}

private fun nthRoot(n: Int, a: BigDecimal, p: BigDecimal): BigDecimal {
    require(a.compareTo(BigDecimal.ZERO) >= 0) { "nth root can only be calculated for positive numbers" }
    if (a == BigDecimal.ZERO) {
        return BigDecimal.ZERO
    }
    var xPrev: BigDecimal = a
    var x: BigDecimal = a.divide(BigDecimal(n), SCALE, ROUNDING_MODE) // starting "guessed" value...
    while (x.subtract(xPrev).abs().compareTo(p) > 0) {
        xPrev = x
        x = BigDecimal.valueOf(n - 1.0)
            .multiply(x)
            .add(a.divide(x.pow(n - 1), SCALE, ROUNDING_MODE))
            .divide(BigDecimal(n), SCALE, ROUNDING_MODE)
    }
    return x
}

fun BigDecimal.sqrt(SCALE: Int): BigDecimal? {
    var x0 = BigDecimal("0")
    var x1 = BigDecimal(Math.sqrt(this.toDouble()))
    while (x0 != x1) {
        x0 = x1
        x1 = this.divide(x0, SCALE, ROUND_HALF_UP)
        x1 = x1.add(x0)
        x1 = x1.divide(BigDecimal.valueOf(2), SCALE, ROUND_HALF_UP)
    }
    return x1
}
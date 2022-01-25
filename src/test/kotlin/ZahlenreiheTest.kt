import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ZahlenreiheTest {
    @Test
    fun test1() {
        val zahlenreihe = listOf(
            BigDecimal.valueOf(0.3),
            BigDecimal.valueOf(1.2),
            BigDecimal.valueOf(0.0),
            BigDecimal.valueOf(2.1),
            BigDecimal.valueOf(0.3)
        )
        analyze(zahlenreihe)
    }

    @Test
    fun test2() {
        val zahlenreihe = listOf(
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(7),
            BigDecimal.valueOf(7),
            BigDecimal.valueOf(7),
            BigDecimal.valueOf(8),
            BigDecimal.valueOf(9),
            BigDecimal.valueOf(11),
            BigDecimal.valueOf(13),
            BigDecimal.valueOf(13),
            BigDecimal.valueOf(13),
            BigDecimal.valueOf(15),
            BigDecimal.valueOf(16),
        )
        analyze(zahlenreihe)
    }

    @Test
    fun test3() {
        val zahlenreihe = listOf(
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(7),
            BigDecimal.valueOf(7),
            BigDecimal.valueOf(7),
            BigDecimal.valueOf(8),
            BigDecimal.valueOf(9),
            BigDecimal.valueOf(11),
            BigDecimal.valueOf(13),
            BigDecimal.valueOf(13),
            BigDecimal.valueOf(13),
            BigDecimal.valueOf(15),
            BigDecimal.valueOf(16),
            BigDecimal.valueOf(17),
        )
        analyze(zahlenreihe)
    }

    @Test
    fun test4() {
        val zahlenreihe = listOf(
            BigDecimal.valueOf(1),
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(5),
        )
        analyze(zahlenreihe)
    }

    @Test
    fun test5() {
        val zahlenreihe = listOf(
            BigDecimal.valueOf(100),
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(4),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(5),
            BigDecimal.valueOf(5),
        )
        analyze(zahlenreihe)
    }
}
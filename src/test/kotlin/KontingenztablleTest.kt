import org.junit.jupiter.api.Test

class KontingenztablleTest {

    @Test
    fun test1() {
        calc(
            listOf(
                listOf(35, 15),
                listOf(11, 39)
            )
        )
    }

    @Test
    fun test2() {
        calc(
            listOf(
                listOf(97, 23),
                listOf(26, 94)
            )
        )
    }

    @Test
    fun test3() {
        calc(
            listOf(
                listOf(45, 12, 13),
                listOf(15, 8, 7)
            )
        )
    }
}
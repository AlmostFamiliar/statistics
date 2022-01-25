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

    @Test
    fun test4() {
        calc(
            listOf(
                listOf(1, 0),
                listOf(0, 1)
            )
        )
    }

    @Test
    fun test5() {
        calc(
            listOf(
                listOf(1, 3),
                listOf(3, 5)
            )
        )
    }
}
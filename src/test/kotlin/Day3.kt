import io.kotest.matchers.shouldBe
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day3 {
    private val sample = """
        d9Ō
        uwI.E9GvrnWļbzO
        ž-2á
        Ģ952W*F4
        ?O6JQf
        xi~Rťfsa
        r_j4XcHŔB
        71äĜ3
    """.trimIndent().trim().split("\n")

    private val data = Path("src/test/resources/${javaClass.simpleName}.txt").readText().split("\n")

    private fun calculate(data: List<String>): Int = data.count { p ->
        p.length in 4..12 &&
                p.any { c -> c.isDigit() } &&
                p.any { c -> c.isUpperCase() } &&
                p.any { c -> c.isLowerCase() } &&
                p.any { it.code !in 0..127 }
    }


    @Test
    fun test() {
        calculate(sample) shouldBe 2
        calculate(data) shouldBe 509
    }
}

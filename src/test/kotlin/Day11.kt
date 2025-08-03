import io.kotest.matchers.shouldBe
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day11 {
    private val sample = """
        σζμ γ' ωοωλδθαξλδμξρ οπξρδυζ οξκτλζσθρ Ξγτρρδτρ.
        αφτ κ' λαλψφτ ωπφχλρφτ δξησηρζαλψφτ φελο, Φκβωωλβ.
        γ βρφαγζ ωνψν ωγφ πγχρρφ δρδαθωραγζ ρφανφ.
    """.trimIndent().split("\n")

    private val data = Path("src/test/resources/${javaClass.simpleName}.txt").readText().split("\n")

    private val lower = "αβγδεζηθικλμνξοπρστυφχψω"
    private val upper = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ"

    private val num = lower.length

    private val lowerChars = lower.toSet()
    private val upperChars = upper.toSet()

    private val nextLower = lower.mapIndexed { i, c -> c to (lower[(i + 1) % num]) }.toMap() + mapOf('ς' to 'τ')
    private val nextUpper = upper.mapIndexed { i, c -> c to (upper[(i + 1) % num]) }.toMap()

    private fun caesar(text: String): String {
        return text.map { c ->
            when (c) {
                in upperChars -> nextUpper[c]!!
                in lowerChars -> nextLower[c]!!
                else -> c
            }
        }.joinToString("")
    }

    private val odysseus = setOf(
        "Οδυσσευς",
        "Οδυσσεως",
        "Οδυσσει",
        "Οδυσσεα",
        "Οδυσσευ",
    )

    fun calculate(data: List<String>): Int {
        var result = 0
        for (d in data) {
            var t = d
            for (i in 0..<num) {
                if (odysseus.any { it in t }) {
                    result += i
                    break
                }
                t = caesar(t)
            }
        }
        return result
    }

    @Test
    fun test() {
        calculate(sample) shouldBe 19
        calculate(data) shouldBe 639
    }
}

import io.kotest.matchers.shouldBe
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day1 {
    private val sample = """
        néztek bele az „ártatlan lapocskába“, mint ahogy belenézetlen mondták ki rá a halálos itéletet a sajtó csupa 20–30 éves birái s egyben hóhérai.
        livres, et la Columbiad Rodman ne dépense que cent soixante livres de poudre pour envoyer à six milles son boulet d'une demi-tonne.  Ces
        Люди должны были тамъ и сямъ жить въ палаткахъ, да и мы не были помѣщены въ посольскомъ дворѣ, который также сгорѣлъ, а въ двухъ деревянныхъ
        Han hade icke träffat Märta sedan Arvidsons middag, och det hade gått nära en vecka sedan dess. Han hade dagligen promenerat på de gator, där
    """.trimIndent().split("\n")

    private val data = Path("src/test/resources/Day1.txt").readText().trim().split("\n")

    private fun calculate(data: List<String>): Int {
        var cost = 0
        data.forEachIndexed { index, d ->
            val s = d.toByteArray().size <= 160
            val t = d.length <= 140
            when {
                s && t -> cost += 13
                s -> cost += 11
                t -> cost += 7
            }}
        return cost
    }

    @Test
    fun test() {
        calculate(sample) shouldBe 31
        calculate(data) shouldBe 107989
    }
}

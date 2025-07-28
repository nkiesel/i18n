import io.kotest.matchers.shouldBe
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day6 {
    private val sample = """
        geléet
        träffs
        religiÃ«n
        tancées
        kÃ¼rst
        roekoeÃ«n
        skälen
        böige
        fÃ¤gnar
        dardÃ©es
        amènent
        orquestrÃ¡
        imputarão
        molières
        pugilarÃÂ£o
        azeitámos
        dagcrème
        zÃ¶ger
        ondulât
        blÃ¶kt

           ...d...
            ..e.....
             .l...
          ....f.
        ......t..
    """.trimIndent().split("\n")

    private val data = Path("src/test/resources/Day6.txt").readText().split("\n")

    private fun calculate(data: List<String>): Int {
        val words = data.filter { it.isNotEmpty() && it[0] != ' ' && it[0] != '.' }.mapIndexed { i, it ->
            var w = it
            if ((i + 1) % 3 == 0) w = String(w.toByteArray(Charsets.ISO_8859_1))
            if ((i + 1) % 5 == 0) w = String(w.toByteArray(Charsets.ISO_8859_1))
            w
        }
        val puzzle = data.filter { '.' in it }.map { Regex(it.trim(), RegexOption.IGNORE_CASE) }
        var sum = 0
        for (p in puzzle) {
            val index = words.indexOfFirst { p.matchEntire(it) != null }
            sum += (index + 1)
        }
        return sum
    }


    @Test
    fun test() {
        calculate(sample) shouldBe 50
        calculate(data) shouldBe 11252
    }
}

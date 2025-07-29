import io.kotest.matchers.shouldBe
import java.text.Normalizer
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day8 {
    private val sample = """
        iS0
        V8AeC1S7KhP4Ļu
        pD9Ĉ*jXh
        E1-0
        ĕnz2cymE
        tqd~üō
        IgwQúPtd9
        k2lp79ąqV
    """.trimIndent().split("\n")

    private val data = Path("src/test/resources/${javaClass.simpleName}.txt").readText().split("\n")

    private fun CharSequence.normalize(): String = this.toList().joinToString("") { i ->
        Normalizer.normalize(i.toString(), Normalizer.Form.NFD)[0].toString().lowercase()
    }

    private val vowels = setOf('a', 'e', 'i', 'o', 'u')

    private fun Char.isVowel() = this in vowels

    private fun calculate(data: List<String>): Int {
        return data.filterNot { it.isBlank() }.map { it.normalize() }.count { p ->
            p.length in 4..12 &&
                    p.any { it.isDigit() } &&
                    p.any { it.isVowel() } &&
                    p.any { it.isLetter() && !it.isVowel() } &&
                    p.toSet().size == p.length
        }
    }

    @Test
    fun test() {
        calculate(sample) shouldBe 2
        calculate(data) shouldBe 809
    }
}

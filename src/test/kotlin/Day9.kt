import Day9.Format.*
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day9 {
    private val sample = """
        16-05-18: Margot, Frank
        02-17-04: Peter, Elise
        06-02-29: Peter, Margot
        31-09-11: Elise, Frank
        09-11-01: Peter, Frank, Elise
        11-09-01: Margot, Frank
    """.trimIndent().split("\n")

    private val data = Path("src/test/resources/${javaClass.simpleName}.txt").readText().split("\n")

    private enum class Format {
        DMY, MDY, YMD, YDM
    }

    private fun valid(y: Int, m: Int, d: Int): Boolean {
        val year = when {
            y < 20 -> y + 2000
            y > 20 -> y + 1900
            m == 1 && d == 1 -> 2000
            else -> 1900
        }
        try {
            LocalDate(year, m, d)
            return true
        } catch (_: Exception) {
            return false
        }
    }

    private fun calculate(data: List<String>): String {
        val formats = mutableMapOf<String, Set<Format>>()

        data.filterNot { it.isBlank() }.forEach { l ->
            val (a, b, c) = l.substringBefore(':').split('-').map { it.toInt() }
            val ppl = l.substringAfter(':').split(',').map { it.trim() }
            val possible = buildSet {
                if (valid(c, b, a)) add(DMY)
                if (valid(c, a, b)) add(MDY)
                if (valid(a, b, c)) add(YMD)
                if (valid(a, c, b)) add(YDM)
            }
            for (p in ppl) {
                val s = formats[p]
                if (s == null) {
                    formats[p] = possible
                } else {
                    formats[p] = s intersect possible
                }
            }
        }

        val days = mapOf(
            "11-09-01" to DMY,
            "09-11-01" to MDY,
            "01-09-11" to YMD,
            "01-11-09" to YDM,
        )

        return buildSet {
            data.filterNot { it.isBlank() }.forEach { l ->
                val c = days[l.substringBefore(':')]
                if (c != null) {
                    val ppl = l.substringAfter(':').split(',').map { it.trim() }.toSet()
                    formats.entries.filter { it.key in ppl && c in it.value }.forEach { add(it.key) }
                }
            }
        }.sorted().joinToString(" ")
    }

    @Test
    fun test() {
        calculate(sample) shouldBe "Margot Peter"
        calculate(data) shouldBe "Amelia Amoura Hugo Jack Jakob Junior Mateo"
    }
}

import io.kotest.matchers.shouldBe
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class Day2 {
    private val sample = """
        2019-06-05T08:15:00-04:00
        2019-06-05T14:15:00+02:00
        2019-06-05T17:45:00+05:30
        2019-06-05T05:15:00-07:00
        2011-02-01T09:15:00-03:00
        2011-02-01T09:15:00-05:00
    """.trimIndent()

    private val data = Path("src/test/resources/Day2.txt").readText()

    @OptIn(ExperimentalTime::class)
    private fun calculate(data: String): String = data.trim()
        .split("\n")
        .map { Instant.parse(it).toString().replace("Z", "+00:00") }
        .groupingBy { it }.eachCount()
        .entries.first { it.value >= 4 }.key


    @Test
    fun test() {
        calculate(sample) shouldBe "2019-06-05T12:15:00+00:00"
        calculate(data) shouldBe "2020-10-25T01:30:00+00:00"
    }
}

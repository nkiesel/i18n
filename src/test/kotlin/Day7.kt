import io.kotest.matchers.shouldBe
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class Day7 {
    private val sample = """
        2012-11-05T09:39:00.000-04:00	969	3358
        2012-05-27T17:38:00.000-04:00	2771	246
        2001-01-15T22:27:00.000-03:00	2186	2222
        2017-05-15T07:23:00.000-04:00	2206	4169
        2005-09-02T06:15:00.000-04:00	1764	794
        2008-03-23T05:02:00.000-03:00	1139	491
        2016-03-11T00:31:00.000-04:00	4175	763
        2015-08-14T12:40:00.000-03:00	3697	568
        2013-11-03T07:56:00.000-04:00	402	3366
        2010-04-16T09:32:00.000-04:00	3344	2605
    """.trimIndent().split("\n")

    private val data = Path("src/test/resources/${javaClass.simpleName}.txt").readText().split("\n")

    private val zones = listOf("America/Halifax", "America/Santiago").map { TimeZone.of(it) }

    @OptIn(ExperimentalTime::class)
    private fun tsName(dateTime: String): TimeZone {
        val i = Instant.parse(dateTime)
        val ldt = LocalDateTime.parse(dateTime.dropLast(6))
        return zones.first { ldt.toInstant(it) == i }
    }

    @OptIn(ExperimentalTime::class)
    private fun calculate(data: List<String>): Int {
        return data.filter { it.isNotBlank() }.map { it.split(Regex("""\s+""")) }.withIndex().sumOf { row ->
            val (time, plus, minus) = row.value
            val ts = tsName(time)
            var t = Instant.parse(time)
            t = t.minus(minus.toInt(), DateTimeUnit.MINUTE)
            t = t.plus(plus.toInt(), DateTimeUnit.MINUTE)
            (row.index + 1) * t.toLocalDateTime(ts).hour
        }
    }


    @Test
    fun test() {
        calculate(sample) shouldBe 866
        calculate(data) shouldBe 32152346
    }
}

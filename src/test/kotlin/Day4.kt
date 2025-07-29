import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test
import kotlin.time.ExperimentalTime

class Day4 {
    private val sample = """
        Departure: Europe/London                  Mar 04, 2020, 10:00
        Arrival:   Europe/Paris                   Mar 04, 2020, 11:59

        Departure: Europe/Paris                   Mar 05, 2020, 10:42
        Arrival:   Australia/Adelaide             Mar 06, 2020, 16:09

        Departure: Australia/Adelaide             Mar 06, 2020, 19:54
        Arrival:   America/Argentina/Buenos_Aires Mar 06, 2020, 19:10

        Departure: America/Argentina/Buenos_Aires Mar 07, 2020, 06:06
        Arrival:   America/Toronto                Mar 07, 2020, 14:43

        Departure: America/Toronto                Mar 08, 2020, 04:48
        Arrival:   Europe/London                  Mar 08, 2020, 16:52
    """.trimIndent().trim().split("\n")

    private val data = Path("src/test/resources/${javaClass.simpleName}.txt").readText().split("\n")

    @OptIn(ExperimentalTime::class)
    private fun calculate(data: List<String>): Int {
        val r = Regex("""^\w+:\s+(\S+)\s+(.+)""")
        val customFormat = LocalDateTime.Format {
            monthName(MonthNames.ENGLISH_ABBREVIATED); char(' '); day(); chars(", "); year()
            chars(", "); hour(); char(':'); minute()
        }

        var minutes = 0L
        var departure = 0L
        for (l in data.filter { it.isNotBlank() }) {
            val m = r.matchEntire(l)
            check(m != null)
            val timeZone = TimeZone.of(m.groupValues[1])
            val localDateTime = customFormat.parse(m.groupValues[2])
            val i = localDateTime.toInstant(timeZone).epochSeconds / 60
            if (departure == 0L) {
                departure = i
            } else {
                minutes += i - departure
                departure = 0
            }
        }
        return minutes.toInt()
    }


    @Test
    fun test() {
        calculate(sample) shouldBe 3143
        calculate(data) shouldBe 16451
    }
}

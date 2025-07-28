import io.kotest.matchers.shouldBe
import jdk.internal.util.random.RandomSupport.AbstractSpliteratorGenerator.ints
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.streams.asSequence
import kotlin.streams.toList
import kotlin.test.Test
import kotlin.time.ExperimentalTime

class Day5 {
    private val sample = """
 ⚘   ⚘ 
  ⸫   ⸫
🌲   💩  
     ⸫⸫
 🐇    💩
⸫    ⸫ 
⚘🌲 ⸫  🌲
⸫    🐕 
  ⚘  ⸫ 
⚘⸫⸫   ⸫
  ⚘⸫   
 💩  ⸫  
     ⸫⸫
    """.trimIndent().split("\n")

    private val poop = "💩"

    private fun String.splitToCodePoints(): List<String> {
        return codePoints().asSequence().map { Character.toString(it) }.toList()
//        val ints = codePoints().toArray()
//        return ints.indices.map { i -> String(ints, i, 1) }
    }

    private val data = Path("src/test/resources/Day5.txt").readText().split("\n")

    @OptIn(ExperimentalTime::class)
    private fun calculate(data: List<String>): Int {
        val area = data.filter { it.isNotBlank() }.map { it.splitToCodePoints() }
        var x = 0
        var y = 0
        var poops = 0
        val maxX = area[0].size
        val maxY = area.size
        while (y < maxY) {
            if (area[y][x] == poop) poops++
            x = (x + 2) % maxX
            y++
        }
        return poops
    }


    @Test
    fun test() {
        calculate(sample) shouldBe 2
        calculate(data) shouldBe 74
    }
}

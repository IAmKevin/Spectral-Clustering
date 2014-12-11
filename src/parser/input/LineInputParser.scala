package parser.input

import scala.io.Source
import scala.collection.mutable.StringBuilder

// Turns each line into 3 grams
class LineInputParser(delimiter: Char) extends AbstractSpanInputParser(delimiter: Char) {

  private val q = 3

  def parse(src: String, dst: String) = {
    val builder = new StringBuilder
    Source.fromFile(src).getLines().foreach { line => builder.append(decomposeToQgrams(q, line) + "\n") }
    writeOutput(builder.subSequence(0, builder.length - 1), dst)
  }
}
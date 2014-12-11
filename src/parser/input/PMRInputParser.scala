package parser.input

import scala.io.Source

// Skip the id of a delimited file where the first entry is the id
// Example line: 1,a1,a2,a3,...
class PMRInputParser(delimiter: Char) extends AbstractSpanInputParser(delimiter: Char) {

  def parse(src: String, dst: String) = {
    val builder = new StringBuilder
    Source.fromFile(src).getLines().foreach { line =>
      val row = line.split(delimiter)
      row.slice(1, row.length).foreach { value => builder.append(value + delimiter) }
      builder(builder.length - 1) = '\n'
    }
    writeOutput(builder.subSequence(0, builder.length - 1), dst)
  }

}
package parser

import java.io.PrintWriter

trait SpanInputParser {
  def parse(src: String, dst: String)

  protected def writeOutput(output: CharSequence, dst: String) = {
    val out = new PrintWriter(dst)
    out.write(output.toString())
    out.close()
  }
}
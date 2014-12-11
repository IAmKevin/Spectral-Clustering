package parser.input

// A parser that does nothing to the input file
// Used when modifying the matrix file directly
class EmptyInputParser(delimiter: Char) extends AbstractSpanInputParser(delimiter: Char) {
  def parse(src: String, dst: String) = {
  }
}
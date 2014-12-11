package parser.input.tomatrix

// A formatter that does nothing
// Used when modifying the matrix file manually
class EmptyMatrixFormatter(delimiter: Char) extends AbstractMatrixFormatter {

  def parse(src: String, dst: String) = {
    
  }
}
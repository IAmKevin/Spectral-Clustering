package span

import java.io.File
import parser.input.AbstractSpanInputParser
import parser.input.tomatrix.SPANMatrixFormatter
import parser.input.tomatrix.AbstractMatrixFormatter

class InputToSpectralClusteringFormat(inputParser: AbstractSpanInputParser, matrixFormatter: AbstractMatrixFormatter) {

  private val parser = inputParser
  private val formatter = matrixFormatter
  private val qgramsFile = "resources/temp/qgrams.csv"

  def formatInput(src: String, dst: String) = {
    parser.parse(src, qgramsFile)
    formatter.parse(qgramsFile, dst)
  }

}
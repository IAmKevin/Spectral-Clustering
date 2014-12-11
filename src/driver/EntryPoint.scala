package driver

import parser.input.PMRInputParser
import parser.input.LineInputParser
import parser.input.tomatrix.SPANMatrixFormatter
import parser.input.tomatrix.ProfileAttrMatrixFormatter
import parser.input.EmptyInputParser
import parser.input.tomatrix.EmptyMatrixFormatter
import clustering.SPANClustering
import clustering.BasicSpectralClustering

object EntryPoint extends App {

  val src = "resources/input/example.txt"
  val dst = "resources/output/blocks"

  // Change the parser to PMR input parser for the other graphs
  val driver = new SpectralClusteringDriver(new LineInputParser(','), new SPANMatrixFormatter(','), new SPANClustering(), true)
  driver.performBlocking(src, dst)
}
package parser.input.tomatrix

import scala.io.Source
import scala.collection.mutable._
import breeze.linalg.DenseVector

// Calculates the unique terms and create the matrix
class SPANMatrixFormatter(delimiter: Char) extends AbstractMatrixFormatter {

  def parse(src: String, dst: String) = {
    val terms = Set[String]()
    Source.fromFile(src).getLines().foreach { line => line.split(delimiter).foreach { value => terms += value } }

    val termMap = terms.toList
    val builder = new StringBuilder()
    Source.fromFile(src).getLines().foreach { line =>
      val row = DenseVector.zeros[Int](termMap.size)
      line.split(delimiter).foreach { value => row(termMap.indexOf(value)) = 1 }
      builder.append(vectorToString(row) + "\n")
    }
    writeOutput(builder.subSequence(0, builder.length - 1), dst)
  }

}
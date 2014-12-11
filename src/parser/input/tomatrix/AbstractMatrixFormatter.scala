package parser.input.tomatrix

import parser.SpanInputParser
import breeze.linalg.DenseVector
import breeze.linalg.DenseMatrix

abstract class AbstractMatrixFormatter extends SpanInputParser {
  def parse(src: String, dst: String)

  protected def vectorToString(vector: DenseVector[Int]): CharSequence = {
    val vectorStringBuilder = new StringBuilder()
    vector.foreach { entry => vectorStringBuilder.append(entry + " ") }
    vectorStringBuilder.subSequence(0, vectorStringBuilder.length - 1)
  }

}
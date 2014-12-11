package parser.input.tomatrix

import scala.io.Source
import scala.collection.mutable._
import breeze.linalg.DenseVector
import breeze.linalg.DenseMatrix

// Calculates unique terms and creates a Laplacian Matrix
class ProfileAttrMatrixFormatter(delimiter: Char) extends AbstractMatrixFormatter {

  def parse(src: String, dst: String) = {
    val terms = Set[String]()
    var numRecords = 0
    Source.fromFile(src).getLines().foreach { line =>
      line.split(delimiter).foreach { value => terms += value }
      numRecords += 1
    }

    val termMap = terms.toList
    val matrixTop = new StringBuilder()
    var matrixBottom = DenseMatrix.zeros[Int](0, numRecords + termMap.size)
    var rowNum = 0
    Source.fromFile(src).getLines().foreach { line =>
      val row = DenseVector.zeros[Int](numRecords + termMap.size)
      var numEdges = 0
      line.split(delimiter).foreach { value => 
        row(numRecords + termMap.indexOf(value)) = -1 
        numEdges += 1  
      }
      row(rowNum) = numEdges
      rowNum += 1
      matrixTop.append(vectorToString(row) + "\n")
      matrixBottom = DenseMatrix.vertcat(matrixBottom, row.toDenseMatrix)
    }

    val builder = new StringBuilder()
    builder.append(matrixTop)
    builder.append(matrixQuadrant3ToString(matrixBottom, numRecords))
    writeOutput(builder, dst)
  }

  private def matrixQuadrant3ToString(matrix: DenseMatrix[Int], numRecords: Int): CharSequence = {
    val matrixStringBuilder = new StringBuilder()
    var rowNum = matrix.rows
    for (i <- numRecords to matrix.cols - 1) {
      val row = DenseVector.zeros[Int](matrix.cols)
      var numEdges = 0
      for (j <- 0 to matrix.rows - 1) {
        if (matrix(j, i) != 0) {
          row(j) = matrix(j, i)
          numEdges += 1
        }
      }
      row(rowNum) = numEdges
      rowNum += 1
      matrixStringBuilder.append(vectorToString(row) + "\n")
    }
    matrixStringBuilder.subSequence(0, matrixStringBuilder.length - 1)
  }

}
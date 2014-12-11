package span

import scala.io.Source
import java.io.PrintWriter
import parser.input.LineInputParser
import parser.input.AbstractSpanInputParser
import breeze.linalg.DenseVector
import stats.SpanStatsLogger
import clustering.SPANClustering
import parser.input.tomatrix.AbstractMatrixFormatter
import clustering.SpectralClustering
import java.nio.file.Files

class SpectralClusteringDriver(parser: AbstractSpanInputParser, matrixFormatter: AbstractMatrixFormatter, clusteringAlgorithm: SpectralClustering, writeBlocks: Boolean) {

  private val formatter = new InputToSpectralClusteringFormat(parser, matrixFormatter)
  private val clusteringAlg = clusteringAlgorithm
  private val matrixFile = "resources/temp/matrix.txt"
  private val statsFile = "resources/output/stats.csv"
  private val statsLogger = new SpanStatsLogger()

  def performBlocking(src: String, dst: String) = {
    // Run Algorithm
    val start = System.nanoTime()
    formatter.formatInput(src, matrixFile)
    val partitionVector = clusteringAlg.cluster(matrixFile)

    // Log stats
    val output = new PrintWriter(statsFile)
    output.write("Number of Rows, Runtime(ms), Used Memory(bytes)\n")
    statsLogger.logStats(output, start, -1)
    output.close()

    // Write Partition
    if (writeBlocks) {
      reconstructRecords(src, dst + "1.txt", partitionVector, true)
      reconstructRecords(src, dst + "2.txt", partitionVector, false)
    }
  }

  private def reconstructRecords(src: String, dst: String, partitionVector: DenseVector[Double], writeNegs: Boolean) = {
    var i = 0
    val builder = new StringBuilder()
    Source.fromFile(src).getLines().foreach { line =>
      if (writeNegs) { if (partitionVector(i) < 0) builder.append(line + "\n") }
      else { if (partitionVector(i) >= 0) builder.append(line + "\n") }
      i += 1
    }

    if (builder.length > 0) {
      val out = new PrintWriter(dst)
      out.write(builder.subSequence(0, builder.length - 1).toString())
      out.close()
    } else {
      System.out.println("Warning: " + dst + " was not changed!")
    }
  }
}
package clustering

import breeze.linalg._
import breeze.numerics._
import java.io.File

// Clustering algorithm for Entity Resolution developed by Liangcai Shu, Aiyou Chen, Ming Xiong, and Weiyi Meng
class SPANClustering extends SpectralClustering {

  def cluster(src: String): DenseVector[Double] = {
    // Read B1
    val B1 = csvread(new File(src), ' ', '"')

    // Compute B2
    var tfidf = log(pow(sum(B1(::, *)) :/= B1.rows.toDouble, -1)) // Assumes all columns have at least 1 non-zero entry
    var tfidfM = DenseMatrix.vertcat(tfidf, tfidf)
    while (tfidfM.rows < B1.rows) {
      tfidfM = DenseMatrix.vertcat(tfidfM, tfidf)
    }
    var B2 = B1 :* tfidfM

    // Compute B
    var B = B2 :* B2
    var rownorm = sqrt(sum(B(*, ::)).toDenseMatrix).t

    B = DenseMatrix.horzcat(rownorm, rownorm)
    while (B.cols < B2.cols) {
      B = DenseMatrix.horzcat(B, B)
    }
    B = B2 :/= B

    // Compute D
    var one = DenseVector.ones[Double](B.rows)
    var D = inv(sqrt(diag(B * (B.t * one))))

    // Compute C
    var C = D * B

    // Partition Records
    svd(C).leftVectors(::, 1) // Built-in SVD
    //orthoIt(C) // Orthogonal Iteration
  }

  private def orthoIt(C: DenseMatrix[Double]): DenseVector[Double] = {
    var Q = DenseMatrix.zeros[Double](2, C.rows)
    Q(0, 0) = 0
    Q(1, 1) = 1
    for (i <- 0 to 10) {
      val A = C * C.t * Q.t
      val u1 = A(::, 0)
      val e1 = u1 :/= norm(u1)
      val u2 = A(::, 1) - (e1 :* (e1.t * A(::, 1)))
      val e2 = u2 :/= norm(u2)
      Q = DenseMatrix.vertcat(e1.toDenseMatrix, e2.toDenseMatrix)
    }
    Q.t(::, 1)
  }

}
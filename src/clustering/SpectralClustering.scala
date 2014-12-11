package clustering

import breeze.linalg.DenseVector

trait SpectralClustering {
  def cluster(src: String): DenseVector[Double]
}
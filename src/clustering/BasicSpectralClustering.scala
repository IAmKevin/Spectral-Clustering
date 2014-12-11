package clustering

import breeze.linalg._
import java.io.File

// Spectral Partitioning through the second smallest eigenvector of a Laplacian Matrix
class BasicSpectralClustering extends SpectralClustering {

  def cluster(src: String): DenseVector[Double] = {
    val laplacian = csvread(new File(src), ' ', '"')
    val eigens = eig(laplacian)
    val eigenvals = eigens.eigenvalues
    
    // Find index of smallest eigenvalue
    var smallest = eigens.eigenvalues(0)
    var idxOfSmallest = 0
    for (i <- 1 to eigenvals.length - 1) {
      if (eigenvals(i) < smallest) {
        smallest = eigenvals(i)
        idxOfSmallest = i
      }
    }

    // Find index of second smallest eigenvalue
    var smallest2Start = 0
    if (idxOfSmallest == 0) {
      smallest2Start = 1
    }
    var smallest2 = eigens.eigenvalues(smallest2Start)
    var idxOfSmallest2 = smallest2Start
    for (i <- smallest2Start + 1 to eigenvals.length - 1) {
      if (i != idxOfSmallest && eigenvals(i) < smallest2) {
        smallest2 = eigenvals(i)
        idxOfSmallest2 = i
      }
    }
    
    // Return the eigenvector corresponding to the second smallest eigenvalue
    eigens.eigenvectors(::, idxOfSmallest2)
  }

}
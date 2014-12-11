package stats

import java.io.PrintWriter

class SpanStatsLogger {

  def logStats(output: PrintWriter, start: Long, numrows: Int) = {
    System.gc()
    val runtime = ((System.nanoTime() - start) / (1000000))
    val free_memory = Runtime.getRuntime().freeMemory()
    val available_memory = Runtime.getRuntime().totalMemory()

    output.write(numrows + ", " + runtime + ", " + (available_memory - free_memory) + "\n")
  }

}
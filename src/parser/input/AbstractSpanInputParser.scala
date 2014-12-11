package parser.input

import parser.SpanInputParser

abstract class AbstractSpanInputParser(delimiter: Char) extends SpanInputParser {

  private val pad1 = '#'
  private val pad2 = '$'
  private val entryDelimiter = delimiter

  def decomposeToQgrams(q: Int, record: String): String = {
    // Initialize record with pad1 prepended and pad2 postpended
    decomposeToQgramsHelper(q, Array.fill[Char](q - 1)(pad1).mkString.concat(record.replace(' ', '_')).concat(Array.fill[Char](q - 1)(pad2).mkString), new StringBuilder())
  }

  private def decomposeToQgramsHelper(q: Int, record: String, result: StringBuilder): String = {
    if (record.length() > 2)
      decomposeToQgramsHelper(q, record.substring(1), result.append(record.substring(0, 3)).append(entryDelimiter))
    else
      result.substring(0, result.length - 1).toString()
  }

  def getDelimiter(): Char = {
    entryDelimiter
  }

  def parse(src: String, dst: String)
}
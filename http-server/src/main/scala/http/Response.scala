import java.io.FileNotFoundException

import scala.io.Source

case class Response(
  status: String,
  header: String,
  body: String
)

object Response{
  var DOC_PATH: String =  """public/"""

  def generate(request: Request): Response = {

    loadTextFromFile(request.path) match {
      case Left(e) =>
        val body = "<h1>404 Not Found</h1>"

        Response(
          "404",
          createHeader(request.header, "404 Not Found", body.size),
          body
        )
      case Right(body) =>
        Response(
          "200",
          createHeader(request.header, "200 OK", body.size),
          body
        )
    }
  }

  def loadTextFromFile(path: String): Either[java.io.FileNotFoundException, String] = {
    try {
      val source = Source.fromFile(DOC_PATH + path)
      Right( source.getLines.foldLeft(""){ (text, line) => text + line } )
    } catch {
      case e:FileNotFoundException => Left( e ) 
    }
  }

  def createHeader(header: Map[String, String], status: String, length: Int): String = {
    val body = header.foldLeft(""){
      case (b, (k, v)) if k.matches("""X-.*""") => b + (k + ": " + v + "\n")
      case (b, (k, v)) => b
    }

    s"""HTTP/1.1 ${status}
Content-type: text/html
Content-Length: ${length}
${body}
"""
  }

}

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
        Response(
          "404",
          createHeader("404 Not Found"),
          "<h1>404 Not Found</h1>"
        )
      case Right(text) =>
        Response(
          "200",
          createHeader("200 OK"),
          text
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

  def createHeader(status: String): String = {
    s"""HTTP/1.1 ${status}
Content-type: text/html

"""
  }

}

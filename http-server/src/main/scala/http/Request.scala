import java.io.InputStream

case class Request(
  method: String,
  path: String,
  header: Map[String, String],
  body: String
)

object Request{
  def parse(in: InputStream): Request = {
    val header = convStreamToHeader(in)
    val body = header.get("Content-Length") match {
      case Some(len) => convStreamToBody(in, len.toInt)
      case None => ""
    }
      

    Request(
      header("method"),
      header("path"),
      header,
      body
    )
  }

  /***
   * リクエストのヘッダ部分を取得
   * TODO: リファクタ
   * 
   * @param in 
   * @param header
   * @param line
   */
  def convStreamToHeader(
    in: InputStream,
    header: Map[String, String] = Map(),
    line: String = ""
  ): Map[String, String] = (line + in.read.asInstanceOf[Char]) match {
    case li: String if li.head.toByte == 13 || li.head.toByte == 10 => // 改行のみの行の場合、ヘッダーのパースを終了
      header
    case li: String if li.last.toByte == 10 => // メッセージヘッダーの処理
      val statusReg = """([A-Z]+) (.+) HTTP/1.1""".r
      val headerReg = """(.+): (.+)""".r

      li.stripLineEnd match {
        case statusReg(m, p) =>
  	  convStreamToHeader(
            in,
            header
              + ("method" -> m)
              + ("path" -> p),
            ""
          )
        case headerReg(k, v) =>
  	  convStreamToHeader(
            in,
            header
              + (k -> v),
            ""
          )
        case _ =>
          println("メッセージヘッダーの処理 match _")
          println(line)
          println(header)
          header
      }

    case li: String =>
      convStreamToHeader(in, header, li)
    case _ =>
      println("end")
      header
  }

  /***
   * リクエストのボディ部分を取得
   * 
   * @param in 
   * @param length
   * @param body
   */
  def convStreamToBody(
    in: InputStream,
    length: Int,
    body: String = ""
  ): String = {
    if(length <= body.size){
      body
    }else{
      convStreamToBody(in, length, body + in.read.asInstanceOf[Char])
    }
  }
}


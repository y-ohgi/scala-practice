import java.net.{Socket, SocketInputStream}
import java.io.{BufferedReader, InputStreamReader, InputStream}
import java.nio.charset.StandardCharsets

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Http{
  def run(socket: Socket): Future[Unit] =  Future {
    val in = socket.getInputStream
    val out = socket.getOutputStream

    val request: Request = Request.parse(in)
    val response: Response = Response.generate(request)

    out.write( (response.header + response.body).getBytes(StandardCharsets.UTF_8))
    out.flush

    socket.close

    println(s"""[${request.method}] ${response.status} ${request.path}""")
  }
}

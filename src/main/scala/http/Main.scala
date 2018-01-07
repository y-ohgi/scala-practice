import java.net.ServerSocket
import java.net.Socket

object Main {
  val PORT: Int = 8080

  def main(args: Array[String]): Unit = {
    val serverSocket: ServerSocket = new ServerSocket(PORT)

    println("waiting for connection...")

    while(true){
      val socket = serverSocket.accept
      Http.run(socket)
    }
  }
}

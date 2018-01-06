//import akka.actor.{ActorSystem, Props, ActorLogging, Actor}
import java.net.ServerSocket
import java.net.Socket

object Main {
  val PORT: Int = 8001

  def main(args: Array[String]): Unit = {
    val serverSocket: ServerSocket = new ServerSocket(8001)

    println("waiting for connection...")

    while(true){
      val socket = serverSocket.accept
      Http.run(socket)
    }
  }
}


// object Main {
//   val PORT = 8001

//   def main(args: Array[String]): Unit = {
//     val serverSocket: ServerSocket = new ServerSocket(8001)

//     while(true){
//       println("waiting for connection...")

//       val socket = serverSocket.accept
//       println("client connected!")
//       val in = socket.getInputStream
//       val out = socket.getOutputStream

//       println(in)
//       println(out)

//       val response = """HTTP/1.1 200 OK
// Content-Encoding: gzip
// Accept-Ranges: bytes
// Cache-Control: max-age=604800
// Content-Type: text/html
// Date: Fri, 05 Jan 2018 04:57:32 GMT
// Etag: "359670651"
// Expires: Fri, 12 Jan 2018 04:57:32 GMT
// Last-Modified: Fri, 09 Aug 2013 23:54:35 GMT
// Server: ECS (sjc/4E44)

// hoge"""

//       out.write(response.getBytes)
//       out.flush()

//       socket.close()
//       println("connection close")

//     }
//   }
// }

//====================


// package http


// import java.net.InetSocketAddress

// import akka.actor.{ActorSystem, Props, ActorLogging, Actor}
// import akka.io.{IO, Tcp}

// /**
//  * 接続されたクライアントとデータのやり取りを行うHandler 
//  */
// class Handler extends Actor {
//   import Tcp._

//   def receive = {
//     // 受け取ったデータをそのまま送信主に返す
//     case Received(data) => sender() ! Write(data)
//     case PeerClosed => context stop self
//   }
// }

// /**
//  * クライアントの接続を受け入れるサーバー
//  */
// class Server(bindAddress: InetSocketAddress) extends Actor with ActorLogging {
//   import Tcp._
//   import context.system

//   IO(Tcp) ! Bind(self, bindAddress)

//   def receive = {
//     case Bound(localAddress) =>
//       log.info("bound on {}...", localAddress)

//     case Connected(remote, local) =>
//       log.info("accepted peer: {}", remote)
//       val handler = context.actorOf(Props[Handler])
//       sender() ! Register(handler)

//     case CommandFailed(_: Bind) =>
//       log.error("bind failed")
//       context stop self
//   }
// }

// /**
//  * メイン関数
//  */
// object EchoServer {
//   def main(args: Array[String]): Unit = {
//     val system = ActorSystem("MySystem")
//     val bindAddress = new InetSocketAddress("localhost", 8001)
//     system.actorOf(Props(classOf[Server], bindAddress))
//   }
// }


// // import java.io.BufferedReader
// // import java.io.InputStreamReader
// // import java.net.ServerSocket
// // import java.net.Socket

// // import java.util.stream.Collectors

// // object Main {
// //   def main(args: Array[String]): Unit = {
// //     println("start >>>")

// //     try {
// //       val server: ServerSocket = new ServerSocket(8001)
// //       val socket: Socket =  server.accept()
// //       val in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))

// //       println("client connected")

// //       // in.lines.forEach(println)

// //       // println("____")

// //       val header = """POST / HTTP/1.1
// // Host: localhost:8001
// // User-Agent: curl/7.51.0
// // Accept: */*
// // Content-Length: 12
// // Content-Type: application/x-www-form-urlencoded

// // Message Body"""

// //       //in.lines()

// //       // while(line != null) {
// //       //   line = in.readLine()
// //       //   println(line)

// //       //   header.append(line + "\n")
// //       // }

// //       println(header)

// //       //val header0 = readLine(in)

      

// //     } catch {
// //       case e: Exception => println(e.getMessage)
// //     } finally {
// //       println("finaly")
// //     }

// //     println("<<< end")

// //   }

// //   def readLine(in: BufferedReader, header: String = ""): String = in.readLine match {
// //     case str: String =>  readLine(in, header + str + "\n")
// //     case null        =>  header
// //   }

// // }


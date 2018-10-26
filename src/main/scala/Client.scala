import java.io.{DataInputStream, DataOutputStream}
import java.net.Socket
import scala.io.StdIn.readLine

object client extends App {
  var client: Option[Socket] = None
  var input: String = ""
  println("[info] Enter 'exit' to exit the app")
  while(input != "exit")
    try {
      //Open your connection to a server, at port 9000
      client = Some(new Socket("127.0.0.1", 9000))
      // Get an input file handle from the socket
      val is = new DataInputStream(client.get.getInputStream)
      val os = new DataOutputStream(client.get.getOutputStream)
      // write to server a string
      input = readLine("Enter a word: ")
      os.writeBytes(input + "\n")
      //read from server a string
      var line: String = is.readLine()
      //printing output
      println(line)
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      client foreach (_.close())
    }
}
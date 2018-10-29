import java.io.{DataInputStream, DataOutputStream}
import java.net.Socket
import scala.io.StdIn.readLine

class Client {
  var client: Option[Socket] = None
  var input: String = ""
  def run(): Unit = {
    println("[info] Enter 'exit' to exit the app")
    while(input != "exit") {
      try {
        client = Some(new Socket("127.0.0.1", 9000))
        val is = new DataInputStream(client.get.getInputStream)
        val os = new DataOutputStream(client.get.getOutputStream)
        input = readLine("Enter a word: ")
        os.writeBytes(input + "\n")
        var line: String = is.readLine()
        println(line)
      } catch {
        case e: Exception => e.printStackTrace()
      } finally {
        client foreach (_.close())
      }
    }
  }
}
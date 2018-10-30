import java.io.{DataInputStream, DataOutputStream}
import java.net.Socket
import scala.io.StdIn.readLine

class Client {
  var client: Option[Socket] = None
  var input: String = ""
  def run(): Unit = {
    val hostname: String = readLine("Enter a hostname: ")
    val port: Int = readLine("Enter port number: ").toInt
    
    println("[info] Enter 'exit' to exit the app")
    while(input != "exit") {
      try {
        client = Some(new Socket(hostname, port))
        val is = new DataInputStream(client.get.getInputStream)
        val os = new DataOutputStream(client.get.getOutputStream)
        input = readLine("Enter a word: ")
        if (input != "exit"){
          os.writeBytes(input + "\n")
          var line: String = is.readLine()
          println(line)
        }
      } catch {
        case e: Exception => e.printStackTrace()
      } finally {
        client foreach (_.close())
      }
      // if (input == "exit"){

      // } else {
      // }
    }
  }
}
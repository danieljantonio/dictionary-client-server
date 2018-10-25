import java.io.{DataInputStream, DataOutputStream}
import java.net.ServerSocket

object server extends App {
  // Register service on port 9000
  val server = new ServerSocket(9000)
  // Register service on port 1234
  val client = server.accept()     

  try {
    // Get a communication stream associated with the socket
    val is = new DataInputStream(client.getInputStream())
    // Get a communication stream associated with the socket
    val os = new DataOutputStream(client.getOutputStream())
    // Read from input stream
    var line: String = is.readLine()
    // sending string
    os.writeBytes("hello from server\n")
    println(line)
  } catch {
    case e: Exception => e.printStackTrace
  } finally {
    // Close the connection, but not the server socket
    client.close()
  }
}

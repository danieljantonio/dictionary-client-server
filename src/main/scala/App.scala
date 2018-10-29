
import scala.io.StdIn.readLine

object App{
	def main(args: Array[String]): Unit = {
		var input: String = "0"
		input = readLine("1: Server\n2: Client\n3: Exit\nEnter your selection: ")
		input match {
			case "1" => runServer()
			case "2" => runClient()
			case "3" => 
			case _ => println("ERR404: Invalid input identified")
		}
	}

	def runServer() {
		Console.flush()
		println("[info] Running Server...")
		val server: Server = new Server()
		server.run()
	}

	def runClient() {
		Console.flush()
		println("[info] Running Client...")
		val client: Client = new Client()
		client.run()
	}
}
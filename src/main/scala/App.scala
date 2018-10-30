
import scala.io.StdIn.readLine

object App{
	def main(args: Array[String]): Unit = {
		var input: String = "0"
		var valid: Boolean = false
		while(!valid)
		try {
			input = readLine("1: Server\n2: Client\n3: Exit\nEnter your selection: ")
			input match {
				case "1" => runServer()
							valid = true
				case "2" => runClient()
							valid = true
				case "3" => valid = true
				case _ => println("ERR404: Invalid input identified")
			}
		} catch {
			case e: Throwable => println()
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
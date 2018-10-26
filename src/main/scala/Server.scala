import java.io.{DataInputStream, DataOutputStream}
import java.net.ServerSocket

import scala.io.Source
import scala.collection.mutable.HashMap

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object server extends App {
  // Register service on port 9000
  val server = new ServerSocket(9000)
  var dictionary: HashMap[String, String] = HashMap[String, String]()
  dictionaryInit()
  while(true) {
    // Register service on port 1234
    val socket = server.accept()
    Future {
      //store local socket references for processing
      val client = socket
      try {
        // Get a communication stream associated with the socket
        val is = new DataInputStream(client.getInputStream())
        // Get a communication stream associated with the socket
        val os = new DataOutputStream(client.getOutputStream())
        // Read from input stream
        var line: String = is.readLine()
        // sending string
        os.writeBytes(searchMeaning(line) + "\n")
      } catch {
        case e: Exception => e.printStackTrace
      } finally {
        // Close the connection, but not the server socket
        client.close()
      }
    }
  }

  def dictionaryInit(): Unit ={
    println("[info] Initializing dictionary")
    val inputs = Source.fromFile("dictionary.txt").getLines
    for (line <- inputs){
      val word :: definition = line.split(":").toList
		  dictionary.update(word, definition.mkString)
    }
    println("[info] Dictionary initialized")
  }

  def searchMeaning(word: String): String = {
    println(s"[info] Searching for the word ${word}")
    //add if statement to check if the word is in the hashmap
    try {
      return (s"The word '${word}' means ${dictionary(word.toLowerCase)}")
    } catch {
      case e: Exception => e.printStackTrace
    }
    return ("The word '${word}' is not found in the dictionary")
  }
}


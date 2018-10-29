import java.io.{DataInputStream, DataOutputStream}
import java.net.ServerSocket

import scala.io.Source
import scala.collection.mutable.HashMap

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Server {
  val server = new ServerSocket(9000)
  var dictionary: HashMap[String, String] = HashMap[String, String]()

  def run(): Unit = {
    dictionaryInit()
    while(true) {
      val socket = server.accept()
      Future {
        val client = socket
        try {
          val is = new DataInputStream(client.getInputStream())
          val os = new DataOutputStream(client.getOutputStream())
          var line: String = is.readLine()
          os.writeBytes(searchMeaning(line) + "\n")
        } catch {
          case e: Exception => e.printStackTrace
        } finally {
          client.close()
        }
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
    if(dictionary.contains(word)){
      return (s"The word '${word}' means ${dictionary(word.toLowerCase)}")
    } else {
      return (s"The word '${word}' is not found in the dictionary")
    }
  }
}


import java.io.{DataInputStream, DataOutputStream}
import java.net.{ServerSocket, InetAddress}

import scala.io.Source
import scala.collection.mutable.HashMap

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import scala.io.StdIn.readLine

class Server {
  val port: Int = readLine("Enter port: ").toInt
  val server = new ServerSocket(port)
  var dictionary: HashMap[String, String] = HashMap[String, String]()
  val hostname: String = InetAddress.getLocalHost.getHostName

  def run(): Unit = {
    dictionaryInit()
    while(true) {
      val socket = server.accept()
      val clientInfo: String = (s"[info] Client ${socket.getInetAddress.getHostName}")
      println(s"${clientInfo} connected")
      Future {
        val client = socket
        try {
          val is = new DataInputStream(client.getInputStream())
          val os = new DataOutputStream(client.getOutputStream())
          var line: String = is.readLine()
          if(line!="exit") {
            println(s"${clientInfo} searched the word ${line}")
            os.writeBytes(searchMeaning(line) + "\n")
          }
        } catch {
          case e: Exception => println()
        } finally {
          client.close()
          println(s"${clientInfo} disconnected")
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
    println(s"Server at Hostname: ${hostname}\tPort: ${port}")
  }

  def searchMeaning(word: String): String = {
    if(dictionary.contains(word)){
      return (s"The word '${word}' means ${dictionary(word.toLowerCase)}")
    } else {
      return (s"The word '${word}' is not found in the dictionary")
    }
  }
}


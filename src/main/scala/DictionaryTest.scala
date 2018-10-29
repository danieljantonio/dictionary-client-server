// Make a server that hosts the library
// Client will search for a word, and
// If word is found the server outputs the definition
// If the word is not found then the server says the word is not yet registered to the server

import scala.io.Source
import scala.collection.mutable.HashMap
import scala.io.StdIn.readLine

class dict{
  var dictionary: HashMap[String, String] = HashMap[String, String]()

  def main(args: Array[String]): Unit = {
    println("[info] Initializing application")
    dictionaryInit()
    val userInput:String = readLine("Enter: ")
    searchMeaning(userInput)
  }

  def dictionaryInit(): Unit ={
    println("[info] Initializing dictionary")
    val inputs = Source.fromFile("dictionary.txt").getLines
    for (line <- inputs){
      val word :: definition = line.split(":").toList
		  dictionary.update(word, definition.mkString)
    }
  }

  def searchMeaning(word: String): Unit = {
    println(s"[info] Searching for the word ${word}")
    println(s"The word '${word}' means ${dictionary(word.toLowerCase)}")
  }

}
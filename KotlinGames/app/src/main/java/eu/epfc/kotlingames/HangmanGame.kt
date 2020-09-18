package eu.epfc.kotlingames


fun playHangGame(){

    println("Hangman start")
   // val wordToGuess :String = generateWordToGuess()
    val lettersPlayedByUsers : MutableList<Char> = mutableListOf()
    //var motMasquer: String = getMaskedWordToGuess(wordToGuess, lettersPlayedByUsers)
   // println("$motMasquer \n")

    var win = false
    for (turn in 0..8){
        println("Type a letter : ")
        val stringInput = readLine()
        if (stringInput != null){
            lettersPlayedByUsers.add(stringInput[0])
            //motMasquer = getMaskedWordToGuess(wordToGuess,lettersPlayedByUsers )
            //println("$motMasquer \n")

            //if (!motMasquer.contains('*')){
                win = true
                break
            }
        }
    }
  //  if (win){
   //     println("YOU WIN !! \n")
  //  }
  //  else{
  //     println("GAME OVER. The word was \"$wordToGuess\" \n")
  //  }
//}





fun getMaskedWordToGuess(wordToGuess : String, playletters : List<Char>): String {
    var maskedWord = ""
    for (letter : Char in wordToGuess){
        if(playletters.contains(letter)){
            maskedWord += letter
        }
        else{
            maskedWord += '*'
        }
    }
    return  maskedWord
}
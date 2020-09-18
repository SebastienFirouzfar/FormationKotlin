package eu.epfc.kotlingames

import android.content.Context
import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.random.Random

class GameManager{


    enum class GameState {
        Running, GameOverWin, GameOverLose
    }

    var tryCount = 0
    var wordToGuess = ""
    var maskedWord = ""
    var playedLetters = mutableListOf<Char>()

    var gameState : GameState = GameState.GameOverWin

    /**
     * start a new game
     */
    fun startNewGame(context: Context){
        tryCount = 0
        wordToGuess = generateWordToGuess(context)
        playedLetters.clear()
        // generate a new word to guess
        maskedWord = getMaskedWordToGuess(wordToGuess, playedLetters)
        gameState = GameState.Running
    }

    /**
     * Update the state of the game with a new letter.
     *
     * This will update properties playedLetters, maskedWord, gameState, and tryCount
     */
    fun playLetter(letter: Char){

        if (!playedLetters.contains(letter)) {

            // mettre à jour tryCount, seulement si la lettre n'a pas déjà été jouée
            if (!wordToGuess.contains(letter)) {
                tryCount++
            }

            // ajouter la lettre à la propriété playLetters
            playedLetters.add(letter)

            // mise à jour de la propriété maskedWord
            maskedWord = getMaskedWordToGuess(wordToGuess, playedLetters)

            // si plus de 6 tentatives
            if (tryCount >= 6) {
                // it's game over
                gameState = GameState.GameOverLose
            }

            // s'il n'y a plus d'astérisques dans le mot masqué -> le mot est deviné
            if (!maskedWord.contains('*')) {
                // switch to win mode
                gameState = GameState.GameOverWin
            }
        }
    }

    /**
     * Return a word randomly chosen in a dictionary file
     */
    private fun generateWordToGuess(context : Context) : String {

        val assetManager : AssetManager = context.assets
        val inputStream : InputStream = assetManager.open("dictionary.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val wordList : MutableList<String> = mutableListOf()
        while (reader.ready()) {
            val line = reader.readLine()
            wordList.add(line)
        }
        val randomIndex = Random.nextInt(wordList.count()-1)
        return wordList[randomIndex].toLowerCase()
    }

    /**
     * generate a word masked with asterisks (*), based on a word to guess, and on the
     * letters already used by the player
     * @param wordToGuess : the word the player must guess to win the game
     * @param playedLetters : letters already used by the player
     */
        private fun getMaskedWordToGuess(wordToGuess : String, playedLetters : List<Char>) : String{

            var maskedWord = ""
            for (letter in wordToGuess){

                if (playedLetters.contains(letter)){
                    maskedWord += letter
                }
                else{
                    maskedWord += '*'
                }
            }
            return maskedWord
    }


}
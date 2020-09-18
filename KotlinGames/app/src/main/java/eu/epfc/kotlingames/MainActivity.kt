package eu.epfc.kotlingames

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var rectangleViews : MutableList<View>
    private val gameManager = GameManager()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //code kotlin et xml et represente la vue

        rectangleViews = mutableListOf()
        rectangleViews.add(findViewById(R.id.rectangle1))
        rectangleViews.add(findViewById(R.id.rectangle2))
        rectangleViews.add(findViewById(R.id.rectangle3))
        rectangleViews.add(findViewById(R.id.rectangle4))
        rectangleViews.add(findViewById(R.id.rectangle5))
        rectangleViews.add(findViewById(R.id.rectangle6))

        for (indicatorView in rectangleViews){
            indicatorView.alpha = 0.2f
        }

        gameManager.startNewGame(this)
        displayGameState(GameManager.GameState.Running)
        val maskedWordText : TextView = findViewById(R.id.maskedWord)
        maskedWordText.text = gameManager.maskedWord
    }

    /**
     * callback called when the user press the ok button
     */
    fun onClickOkButton(view: View) {
        val editText : EditText = findViewById(R.id.editText)
        val stringEditText = editText.text
        // si l'utilisateur a saisi une lettre
        if (stringEditText.isNotEmpty()) {

            gameManager.playLetter(stringEditText[0])

            // mettre à jour les vues représentant tryCount
            updateTryCountViews(gameManager.tryCount)

            // mise à jour maskedWord TextView
            val maskedWordText : TextView = findViewById(R.id.maskedWord)
            maskedWordText.text = gameManager.maskedWord

            // effacer la lettre dans l'EditText
            editText.setText("")

            // afficher le jeuAfficher l'écran si nécessaire
            displayGameState(gameManager.gameState)
        }
    }

    /**
     * update the appearance of the rectangle views representing tryCount
     * @param tryCount the number of tries
     */
    private fun updateTryCountViews(tryCount : Int){

        for (i in 0 until tryCount){
            rectangleViews[i].alpha = 1f
        }
        for (i in tryCount until rectangleViews.size){
            rectangleViews[i].alpha = 0.2f
        }
    }

    /**
     * Lisez un objet GameState et mettez à jour l'interface utilisateur en conséquence (couleur du fond,
     * bouton de réessai, vues représentant le nombre d'essais ... etc
     * @param gameState : objet représentant l'état actuel du jeu
     */
    private fun displayGameState(gameState : GameManager.GameState){
        val maskedWordText : TextView = findViewById(R.id.maskedWord)
        val pageTitle : TextView = findViewById(R.id.page_title)
        val retryButton : Button = findViewById(R.id.retry_button)
        val inputLayout : ViewGroup = findViewById(R.id.input_layout)

        when (gameState){
            GameManager.GameState.GameOverLose -> {
                // mettre à jour le mot masqué TextView
                maskedWordText.text = gameManager.wordToGuess
                pageTitle.text = "GAME OVER"
                maskedWordText.textSize = 50f

                // changer la couleur du fond
                findViewById<LinearLayout>(R.id.root_layout).setBackgroundResource(R.color.red)

                // bouton de réessai de l'affichage
                retryButton.visibility = View.VISIBLE
                inputLayout.visibility = View.INVISIBLE
                hideKeyboard()
            }
            GameManager.GameState.GameOverWin -> {
                // mettre à jour le mot masqué TextView
                maskedWordText.text = gameManager.wordToGuess
                pageTitle.text = "YOU WIN"
                maskedWordText.textSize = 50f

                // change the color of the background
                findViewById<LinearLayout>(R.id.root_layout).setBackgroundResource(R.color.green)

                // bouton de réessai de l'affichage
                retryButton.visibility = View.VISIBLE
                inputLayout.visibility = View.INVISIBLE
                hideKeyboard()
            }
            GameManager.GameState.Running -> {
                pageTitle.text = "GUESS THE WORD"
                // mettre à jour le mot masqué TextView
                maskedWordText.text = gameManager.maskedWord
                maskedWordText.textSize = 24f
                // change the color of the background
                findViewById<LinearLayout>(R.id.root_layout).setBackgroundResource(R.color.white)
                // hide retry button
                retryButton.visibility = View.INVISIBLE
                inputLayout.visibility = View.VISIBLE
            }
        }
    }
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val rootView : View = findViewById(android.R.id.content)
        imm.hideSoftInputFromWindow(rootView.windowToken, 0)
    }

    fun onRetryButton(view: View) {
        gameManager.startNewGame(this)
        displayGameState(GameManager.GameState.Running)
    }
}




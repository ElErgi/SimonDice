package com.example.simondice

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonClickListener: ButtonClickListener
    private lateinit var soundManager: SoundManager
    private lateinit var buttonRepeat: Button // Declarar buttonRepeat como miembro de la clase
    private lateinit var buttonBlue: Button
    private lateinit var buttonRed: Button
    private lateinit var buttonYellow: Button
    private lateinit var buttonGreen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Asignar la referencia de buttonRepeat después de inflar el diseño
        buttonRepeat = findViewById(R.id.buttonRepeat)
        buttonBlue = findViewById(R.id.buttonBlue)
        buttonRed = findViewById(R.id.buttonRed)
        buttonYellow = findViewById(R.id.buttonYellow)
        buttonGreen = findViewById(R.id.buttonGreen)

        // Inicializar el SoundManager
        soundManager = SoundManager(this)

        // Inicializar el ButtonClickListener y configurar los listeners
        buttonClickListener = ButtonClickListener(this, soundManager, listOf(buttonBlue, buttonRed, buttonYellow, buttonGreen))
        buttonClickListener.setButtonClickListeners()
    }

    fun showWinMessage() {
        val textWin = findViewById<TextView>(R.id.textWin)
        textWin.visibility = View.VISIBLE
    }

    fun showLoseMessage() {
        val textLose = findViewById<TextView>(R.id.textLose)
        textLose.visibility = View.VISIBLE
    }
    fun showTeTocaMessage() {
        val messageTextView = findViewById<TextView>(R.id.textTeToca)
        messageTextView.text = "Te toca"
        messageTextView.visibility = View.VISIBLE
    }
    fun hideTeTocaMessage() {
        val textTeToca = findViewById<TextView>(R.id.textTeToca)
        textTeToca.visibility = View.INVISIBLE
    }

}

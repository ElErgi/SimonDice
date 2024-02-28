package com.example.simondice

import android.graphics.Color
import android.os.Handler
import android.widget.Button

class ButtonClickListener(
    private val activity: MainActivity,
    private val soundManager: SoundManager,
    private val buttons: List<Button>
) {

    private var isSequenceRunning = false
    private var sequence = mutableListOf<Int>()
    private lateinit var buttonPlay: Button
    private var userSequence = mutableListOf<Button>()

    fun setButtonClickListeners() {
        buttonPlay = activity.findViewById(R.id.buttonRepeat)
        buttonPlay.text = "Play"
        buttonPlay.isEnabled = true

        buttonPlay.setOnClickListener {
            if (!isSequenceRunning) {
                isSequenceRunning = true
                it.isEnabled = false
                userSequence.clear() // Limpiar userSequence antes de comenzar una nueva secuencia
                startSequence()
            }
        }

        for (button in buttons) {
            button.setOnClickListener {
                if (isSequenceRunning || userSequence.size >= 4) return@setOnClickListener

                val color = button.backgroundTintList?.defaultColor ?: Color.WHITE
                button.setBackgroundColor(Color.parseColor("#CCCCCC"))
                Handler().postDelayed({
                    button.setBackgroundColor(color)
                }, 200)

                userSequence.add(button) // Agregar el botón, no el índice
                soundManager.playSound(buttons.indexOf(button)) // Reproducir el sonido asociado al botón

                if (userSequence.size == 4) {
                    if (checkUserSequence()) {
                        activity.runOnUiThread { activity.showWinMessage() }
                    } else {
                        activity.runOnUiThread { activity.showLoseMessage() }
                    }
                    resetGame()
                }
            }
        }
    }

    private fun startSequence() {
        buttonPlay.isEnabled = false

        sequence.clear()

        repeat(4) {
            sequence.add((0 until buttons.size).random())
        }

        playNextSound(0)

        // Delay para mostrar el mensaje después de que la secuencia haya terminado
        Handler().postDelayed({
            activity.showTeTocaMessage()
        }, 1000 * sequence.size.toLong()) // 1000 ms * número de botones en la secuencia
    }
    private fun playNextSound(index: Int) {
        if (index < sequence.size) {
            val button = buttons[sequence[index]]
            val color = button.backgroundTintList?.defaultColor ?: Color.WHITE
            button.setBackgroundColor(Color.parseColor("#CCCCCC"))
            soundManager.playSound(sequence[index])
            Handler().postDelayed({
                button.setBackgroundColor(color)
                playNextSound(index + 1)
            }, 1000)
        } else {
            enableButtonClickListener()
            buttonPlay.isEnabled = true
        }
    }

    private fun enableButtonClickListener() {
        for (button in buttons) {
            button.isEnabled = true
        }
    }

    private fun checkUserSequence(): Boolean {
        if (userSequence.size != sequence.size) return false
        for (i in sequence.indices) {
            if (buttons.indexOf(userSequence[i]) != sequence[i]) return false
        }
        return true
    }

    private fun resetGame() {
        userSequence.clear()
        for (button in buttons) {
            button.isEnabled = false
        }
        buttonPlay.isEnabled = true
        isSequenceRunning = false
    }
}

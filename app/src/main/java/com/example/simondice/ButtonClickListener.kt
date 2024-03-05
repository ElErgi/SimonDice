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
                userSequence.clear()
                activity.hideWinMessage()
                activity.hideLoseMessage()
                startSequence()
            }
        }

        for (button in buttons) {
            button.setOnClickListener {
                if (!isSequenceRunning && userSequence.size < sequence.size) {
                    val color = button.backgroundTintList?.defaultColor ?: Color.WHITE
                    button.setBackgroundColor(Color.parseColor("#CCCCCC"))
                    Handler().postDelayed({
                        button.setBackgroundColor(color)
                    }, 200)

                    userSequence.add(button)
                    soundManager.playSound(buttons.indexOf(button))

                    if (userSequence.size == sequence.size) {
                        if (checkUserSequence()) {
                            activity.runOnUiThread { activity.showWinMessage() }
                        } else {
                            activity.runOnUiThread { activity.showLoseMessage() }
                        }
                    }
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
            isSequenceRunning = false
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
}

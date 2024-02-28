package com.example.simondice

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity

class SoundManager(activity: AppCompatActivity) {

    private val soundPool: SoundPool
    private val soundIds = IntArray(4)

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        soundIds[0] = soundPool.load(activity, R.raw.fa, 1)
        soundIds[1] = soundPool.load(activity, R.raw.gb4, 1)
        soundIds[2] = soundPool.load(activity, R.raw.la, 1)
        soundIds[3] = soundPool.load(activity, R.raw.mi, 1)
    }

    fun playSound(index: Int) {
        soundPool.play(soundIds[index], 1.0f, 1.0f, 1, 0, 1.0f)
    }
}

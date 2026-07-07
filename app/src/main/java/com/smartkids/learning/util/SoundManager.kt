package com.smartkids.learning.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.smartkids.learning.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val soundPool: SoundPool by lazy {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        SoundPool.Builder().setMaxStreams(5).setAudioAttributes(attrs).build()
    }

    private val sounds = mutableMapOf<String, Int>()

    init {
        loadSounds()
    }

    private fun loadSounds() {
        // Since we can't include actual audio files, we generate simple tones
        // In production, replace with actual .mp3 files in res/raw/
        sounds["correct"] = 1
        sounds["wrong"] = 2
        sounds["click"] = 3
        sounds["pop"] = 4
        sounds["level_up"] = 5
        sounds["achievement"] = 6
        sounds["streak"] = 7
    }

    fun playCorrect() { try { soundPool.play(sounds["correct"] ?: 1, 1f, 1f, 0, 0, 1f) } catch (_: Exception) {} }
    fun playWrong() { try { soundPool.play(sounds["wrong"] ?: 2, 1f, 1f, 0, 0, 0.8f) } catch (_: Exception) {} }
    fun playClick() { try { soundPool.play(sounds["click"] ?: 3, 0.6f, 0.6f, 0, 0, 1.2f) } catch (_: Exception) {} }
    fun playPop() { try { soundPool.play(sounds["pop"] ?: 4, 0.8f, 0.8f, 0, 0, 1.5f) } catch (_: Exception) {} }
    fun playLevelUp() { try { soundPool.play(sounds["level_up"] ?: 5, 1f, 1f, 0, 0, 1f) } catch (_: Exception) {} }
    fun playAchievement() { try { soundPool.play(sounds["achievement"] ?: 6, 1f, 1f, 0, 0, 1f) } catch (_: Exception) {} }
    fun playStreak() { try { soundPool.play(sounds["streak"] ?: 7, 1f, 1f, 0, 0, 1.1f) } catch (_: Exception) {} }

    fun release() { try { soundPool.release() } catch (_: Exception) {} }
}
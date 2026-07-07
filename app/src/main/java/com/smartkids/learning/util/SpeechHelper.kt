package com.smartkids.learning.util

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeechHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var tts: TextToSpeech? = null
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        tts = TextToSpeech(context) { status ->
            _isReady.value = status == TextToSpeech.SUCCESS
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
                tts?.setSpeechRate(0.85f)
                tts?.setPitch(1.2f)
            }
        }
    }

    fun speak(text: String, queueMode: Int = TextToSpeech.QUEUE_ADD) {
        if (_isReady.value && text.isNotBlank()) {
            tts?.speak(text, queueMode, null, "smartkids_utterance")
        }
    }

    fun speakNow(text: String) {
        if (_isReady.value && text.isNotBlank()) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "smartkids_utterance")
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.shutdown()
        tts = null
    }

    fun setLanguage(locale: Locale) {
        tts?.language = locale
    }

    fun setSpeechRate(rate: Float) {
        tts?.setSpeechRate(rate.coerceIn(0.3f, 2.0f))
    }

    fun setPitch(pitch: Float) {
        tts?.setPitch(pitch.coerceIn(0.5f, 2.0f))
    }
}
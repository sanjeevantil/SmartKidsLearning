package com.smartkids.learning

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import kotlin.system.exitProcess

class CrashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crashLog = intent.getStringExtra("CRASH_LOG") ?: "No crash log found"
        
        val textView = TextView(this).apply {
            text = "App Crashed:\n\n$crashLog"
            textSize = 14f
            setPadding(32, 32, 32, 32)
        }
        
        val scrollView = ScrollView(this).apply {
            addView(textView)
        }
        
        setContentView(scrollView)
    }

    companion object {
        fun start(context: Context, crashLog: String) {
            val intent = Intent(context, CrashActivity::class.java).apply {
                putExtra("CRASH_LOG", crashLog)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }
    }
}

package com.smartkids.learning

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(application = SmartKidsApp::class, sdk = [33])
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun launchMainActivity_doesNotCrash() {
        val activity = Robolectric.buildActivity(MainActivity::class.java)
        activity.create().start().resume()
        
        // If it doesn't throw an exception, the test passes
    }
}

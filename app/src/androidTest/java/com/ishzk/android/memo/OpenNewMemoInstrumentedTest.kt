package com.ishzk.android.memo

import android.content.ComponentName
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents.getIntents
import androidx.test.ext.truth.content.IntentSubject.assertThat
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpenNewMemoInstrumentedTest {
    @Before
    fun setup(){
        launchActivity<MainActivity>()
    }

    @get:Rule
    var intentsRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)

    @Test
    fun pushNewMemoButton_test(){
        onView(withId(R.id.fabNewMemoButton))
            .perform(click())

        val intents = getIntents()
        val intent = intents.last()
        assertThat(intent).hasComponent(ComponentName(
            "com.ishzk.android.memo", "com.ishzk.android.memo.NewMemoActivity"
        ))

        onView(withId(R.id.editTextTitle))
            .perform(typeText("test title"))
    }
}
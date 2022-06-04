package com.ishzk.android.memo

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ishzk.android.memo.Repository.MemoRepository
import com.ishzk.android.memo.di.FireStoreMemo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SaveMemoInstrumentedTest {
    @Before
    fun setup(){
        launchActivity<NewMemoActivity>()
    }

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @FireStoreMemo
    @Inject lateinit var memoRepository: MemoRepository

    @Test
    fun pushSaveButton_test(){
        onView(withId(R.id.menu_save_button))
            .perform(click())
    }
}
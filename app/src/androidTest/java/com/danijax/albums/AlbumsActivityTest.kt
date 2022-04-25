package com.danijax.albums

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.danijax.albums.ui.albums.AlbumsActivity
import com.danijax.albums.ui.albums.AlbumsViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
//@Config(application = HiltTestApplication::class)
//@RunWith(RobolectricTestRunner::class)
//@LooperMode(LooperMode.Mode.PAUSED)
class AlbumsActivityTest{

    @get:Rule(order = 0) // 2
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1) // 2
    var activityScenarioRule: ActivityScenarioRule<AlbumsActivity> =
        ActivityScenarioRule(AlbumsActivity::class.java) // 1

    //@BindValue
    //@JvmField
    //val viewModel = mockk<AlbumsViewModel>(relaxed = true)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun testIt(){
        assert(true)
    }
}
package com.example.triple_kill_7_2

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import android.content.pm.ActivityInfo
import android.view.Gravity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Before
import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun testFirstFrag() {
        checkFragmentIntegrity(1)
    }

    @Test
    fun testSecondFrag() {
        openFragment(2, true)
    }

    @Test
    fun testThirdFrag() {
        openFragment(2)
        openFragment(3, true)
    }

    @Test
    fun testAbout() {
        openFragment(4, true)

        openPreviousFrag(true)
        Thread.sleep(THREAD_SLEEP_TIME)
        checkFragmentIntegrity(1)

        openFragment(2, true)
        openFragment(4, true)

        openPreviousFrag(true)
        Thread.sleep(THREAD_SLEEP_TIME)
        checkFragmentIntegrity(2)

        openFragment(3, true)
        openFragment(4, true)

        openPreviousFrag(true)
        Thread.sleep(THREAD_SLEEP_TIME)
        checkFragmentIntegrity(3)
    }

    @Test
    fun testBackFromSecondFrag() {
        openFragment(2, true)
        openFragment(1, true)
    }

    @Test
    fun testBackFromSecondFragViaBackButton() {
        openFragment(2, true)
        openPreviousFrag()
        checkFragmentIntegrity(1)
    }

    @Test
    fun testBackFromThirdFrag() {
        openFragment(2, true)
        openFragment(3, true)
        openFragment(2, true)
    }

    @Test
    fun testBackFromThirdFragViaBackButton() {
        openFragment(2, true)
        openFragment(3, true)
        openPreviousFrag()
        checkFragmentIntegrity(2)
        openPreviousFrag()
        checkFragmentIntegrity(1)
    }




    @Test
    fun testFullBackstackViaUp() {
        openFragment(4, true)
        openPreviousFrag(forceCloseDrawer = true, viaUp = true)
        checkFragmentIntegrity(1)

        openFragment(2, true)
        openFragment(4, true)
        openPreviousFrag(forceCloseDrawer = true, viaUp = true)
        checkFragmentIntegrity(2)

        openFragment(3, true)
        openFragment(4, true)
        openPreviousFrag(forceCloseDrawer = true, viaUp = true)
        checkFragmentIntegrity(3)
    }

    @Test
    fun checkRotationScreen() {
        rotateDevice(true)
        checkFragmentIntegrity(1)
        rotateDevice(false)
        checkFragmentIntegrity(1)

        openFragment(2, true)
        rotateDevice(true)
        checkFragmentIntegrity(2)
        rotateDevice(false)
        checkFragmentIntegrity(2)

        openFragment(3, true)
        rotateDevice(true)
        checkFragmentIntegrity(3)
        rotateDevice(false)
        checkFragmentIntegrity(3)

        openFragment(4, true)
        rotateDevice(true)
        checkFragmentIntegrity(4)
        rotateDevice(false)
        checkFragmentIntegrity(4)
    }

    private fun checkFragmentIntegrity(fragIndex: Int) {
        when(fragIndex) {
            1 -> {
                onView(withId(R.id.firstFragment)).check(matches(isDisplayed()))
                onView(withId(R.id.toSecond)).check(matches(isDisplayed()))
            }
            2 -> {
                onView(withId(R.id.secondFragment)).check(matches(isDisplayed()))
                onView(withId(R.id.toFirst)).check(matches(isDisplayed()))
                onView(withId(R.id.toThird)).check(matches(isDisplayed()))
            }
            3 -> {
                onView(withId(R.id.thirdFragment)).check(matches(isDisplayed()))
                onView(withId(R.id.toFirst)).check(matches(isDisplayed()))
                onView(withId(R.id.toSecond)).check(matches(isDisplayed()))
            }
            4 -> {
                onView(withId(R.id.aboutActivity)).check(matches(isDisplayed()))
                onView(withId(R.id.back)).check(matches(isDisplayed()))
            }
        }
    }

    private fun openFragment(fragIndex: Int, autoCheckIntegrity: Boolean = false) {
        when (fragIndex) {
            1 -> {
                onView(withId(R.id.toFirst)).perform(click())
                if (autoCheckIntegrity)
                    checkFragmentIntegrity(1)
            }
            2 -> {
                onView(withId(R.id.toSecond)).perform(click())
                if (autoCheckIntegrity)
                    checkFragmentIntegrity(2)
            }
            3 -> {
                onView(withId(R.id.toThird)).perform(click())
                if (autoCheckIntegrity)
                    checkFragmentIntegrity(3)
            }
            4 -> {
                openAbout()
                if (autoCheckIntegrity)
                    checkFragmentIntegrity(4)
            }
        }
    }

    private fun openPreviousFrag(forceCloseDrawer: Boolean = false, viaUp: Boolean = false) {
        if (viaUp) {
            onView(ViewMatchers.withContentDescription("Navigate up")).perform(click())
        }
        else {
            pressBack()
        }

        if (forceCloseDrawer) {
            forceCloseDrawer()
        }
    }

    private fun rotateDevice(landscape: Boolean) {
        if (landscape) {
            activityRule.scenario.onActivity { activity ->
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
        else {
            activityRule.scenario.onActivity { activity ->
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
        Thread.sleep(THREAD_SLEEP_TIME)
    }

    private fun getRandomlyToFrag(destinationFragIndex: Int, rejections: Int = 5, startFragIndex: Int = 1,
                                  useUpForReturn: Boolean = false) {
        var currentFragIndex = startFragIndex
        var currentRejections = 0
        var preAboutFragIndex = 0

        val fragTransitions = mapOf(
            1 to listOf(2, 4),
            2 to listOf(1, 3, 4),
            3 to listOf(1, 2, 4),
            4 to listOf(-1)
        )

        while (currentRejections < rejections) {
            val nextFragIndex = fragTransitions[currentFragIndex]!!.random()

            if (nextFragIndex == -1) {
                currentFragIndex = fragTransitions[preAboutFragIndex]!!.random()
                openPreviousFrag(useUpForReturn)
                Thread.sleep(THREAD_SLEEP_TIME)
            }
            else {
                preAboutFragIndex = currentFragIndex
                currentFragIndex = nextFragIndex
            }

            forceCloseDrawer()
            openFragment(currentFragIndex, true)

            if (currentFragIndex == destinationFragIndex)
                currentRejections++
        }
    }

    private fun checkApplicationQuit() {
        try {
            openPreviousFrag()
            assert(false)
        } catch (NoActivityResumedException: Exception) {
            assert(true)
        }
    }

    private fun forceCloseDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close())
        //Thread.sleep(THREAD_SLEEP_TIME)
    }

    private fun openAbout() {
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.aboutActivity))
    }

    companion object {
        private const val THREAD_SLEEP_TIME = 500L
    }
}
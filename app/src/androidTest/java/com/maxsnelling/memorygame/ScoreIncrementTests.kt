package com.maxsnelling.memorygame


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testing that turns increase the score value
 * @author Max Snelling
 * @version 15/07/20
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class ScoreIncrementTests {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    /**
     * Tests that one move increase the score by 1
     */
    @Test
    fun scoreIncrementTests() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.PlayButton), withText("Play"),
                childAtPosition(
                    allOf(
                        withId(R.id.ActivityMain),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val button = onView(
            allOf(
                withText("1"),
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        button.perform(click())

        val button2 = onView(
            allOf(
                withText("4"),
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        button2.perform(click())

        val textView = onView(withId(R.id.gameScore))
        textView.check(matches(withText("Score: 1")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

package com.maxsnelling.memorygame


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testing that the correct number of tiles are being created
 * for each difficulty selected
 * @author Max Snelling
 * @version 15/07/20
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class TileCreationTests {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    /**
     * Tests 4 tiles are created for the minimal difficulty
     */
    @Test
    fun defaultDifficultyTest() {
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
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
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
        button2.check(matches(isDisplayed()))

        val button3 = onView(
            allOf(
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
        button3.check(matches(isDisplayed()))

        val button4 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        button4.check(matches(isDisplayed()))

        val button5 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        button5.check(matches(isDisplayed()))
    }

    /**
     * Tests 8 buttons are created on difficulty 3. If this works then tiles
     * for other difficulties should show.
     */
    @Test
    fun selectedDifficultyTest() {
        val appCompatSpinner = onView(
            allOf(
                withId(R.id.difficultySpinner),
                childAtPosition(
                    allOf(
                        withId(R.id.ActivityMain),
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
        appCompatSpinner.perform(click())

        val appCompatTextView = Espresso.onData(Matchers.anything()).atPosition(1)
        appCompatTextView.perform(click())

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
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
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
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
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
        button2.check(matches(isDisplayed()))

        val button3 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        button3.check(matches(isDisplayed()))

        val button4 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        button4.check(matches(isDisplayed()))

        val button5 = onView(
            allOf(
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
        button5.check(matches(isDisplayed()))

        val button6 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        button6.check(matches(isDisplayed()))

        val button7 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_game),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        button7.check(matches(isDisplayed()))
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

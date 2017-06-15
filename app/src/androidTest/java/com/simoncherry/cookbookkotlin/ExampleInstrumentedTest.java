package com.simoncherry.cookbookkotlin;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.widget.TextView;

import com.simoncherry.cookbookkotlin.ui.activity.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
            };

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.simoncherry.cookbookkotlin", appContext.getPackageName());
    }

    @Test
    public void testShowChannelLayout() {
        onView(withId(R.id.iv_expand)).perform(click());
        onView(withId(R.id.layout_channel)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_expand)).perform(click());
        onView(withId(R.id.layout_channel)).check(matches((not(isDisplayed()))));
    }

    @Test
    public void testQueryRecipe() {
        onView(withId(R.id.search_view)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(replaceText("炒面"),
                pressKey(KeyEvent.KEYCODE_ENTER));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("查询 - 炒面")));
    }

    @Test
    public void testAddChannel() {
        onView(withId(R.id.iv_expand)).perform(click());
        onView(withId(R.id.layout_channel)).check(matches(isDisplayed()));
        onView(withText("素菜")).perform(click());
        onView(withId(R.id.iv_expand)).perform(click());
        onView(withId(R.id.layout_channel)).check(matches((not(isDisplayed()))));

//        onView(withId(R.id.layout_tab)).check(matches(withText("素菜")));

//        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.layout_tab))))
//                .check(matches(withText("素菜")));

//        onView(allOf(withText("素菜"), isDescendantOfA(withId(R.id.layout_tab))))
//                .check(matches(isDisplayed()));

//        onView(allOf(instanceOf(AppCompatTextView.class), isDescendantOfA(withId(R.id.layout_tab))))
//                .check(matches(withText("素菜")));

        ViewInteraction tabView = onView(
                allOf(instanceOf(AppCompatTextView.class), isDescendantOfA(withId(R.id.layout_tab)), Matchers.not(withText("荤菜"))));
        tabView.check(matches(isDisplayed()));
        tabView.check(matches(withText("素菜")));
    }

    @Test
    public void testRemoveChannel() {
        onView(withId(R.id.iv_expand)).perform(click());
        onView(withId(R.id.layout_channel)).check(matches(isDisplayed()));
        onView(allOf(withText("素菜"), withParent(withId(R.id.added_channel_recyclerview)))).perform(swipeRight());
        onView(withId(R.id.iv_expand)).perform(click());
        onView(withId(R.id.layout_channel)).check(matches((not(isDisplayed()))));

        ViewInteraction tabView = onView(allOf(instanceOf(AppCompatTextView.class), isDescendantOfA(withId(R.id.layout_tab)), withText("素菜")));
        tabView.check(doesNotExist());
    }
}

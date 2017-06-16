package com.simoncherry.cookbookkotlin;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
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
    public void testQuerySuggestion() {
        onView(withId(R.id.search_view)).perform(click());
        onView(withText("炒面"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(mMainActivityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
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

    @Test
    public void testOpenRecipeDetail() {
        onView(withId(R.id.fab)).check(doesNotExist());
        onView(allOf(withId(R.id.rv_recipe), withParent(allOf(withId(R.id.layout_recipe), isDisplayed()))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.fab)).check(doesNotExist());
    }

    @Test
    public void testClickCategory() {
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_category));
//        onView(withId(R.id.drawer_layout)).perform(close());
//        onView(withId(R.id.nav_view)).check(matches(not(isDisplayed())));

        /*
        * 查询菜谱分类页面中，有两个RecyclerView：
        * rv_category  | rv_tag
        * 菜品         | [荤菜] [素菜] [汤粥]
        *              | [西点] [主食] [饮品]
        * -------------
        * 菜系         | [鲁菜] [川菜] [粤菜]
        *              | [闽菜] [浙菜] [湘菜]
        *
        * 在rv_tag中，点击前两列任意行的项目都是正常的，但是点击第三列任意行的项目都报错。如以下信息是点击【汤粥】时：
        * android.support.test.espresso.PerformException: Error performing 'performing ViewAction:
        * single click on item matching: holder with view: has descendant: with text: is "汤粥"' on view 'with id: com.simoncherry.cookbookkotlin:id/rv_tag'.
        * ...
        * Caused by: android.support.test.espresso.PerformException: Error performing 'single click
        * - At Coordinates: 926, 313 and precision: 16, 16'
        * on view 'RelativeLayout{
        *   id=2131624119, res-name=layout_root, visibility=VISIBLE, width=210, height=120,
        *   has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=true, is-enabled=true, is-focused=false, is-focusable=false,
        *   is-layout-requested=false, is-selected=false, root-is-layout-requested=false, has-input-connection=false, x=552.0, y=20.0, child-count=1}'.
        * ...
        * Caused by: android.support.test.espresso.PerformException: Error performing 'Send down motion event' on view 'unknown'.
        * ...
        * Caused by: android.support.test.espresso.InjectEventSecurityException: java.lang.SecurityException: Injecting to another application requires INJECT_EVENTS permission
        * ...
        * Caused by: java.lang.SecurityException: Injecting to another application requires INJECT_EVENTS permission
        *
        * 搜索资料发现，报这个错误的直接原因是，点击的地方所在的View是属于另一个应用的，所以没有权限。最大的可能是点击到了软键盘。然而这里并没有弹出软键盘。
        */
//        onView(allOf(withText("汤粥"), isDescendantOfA(withId(R.id.rv_tag)))).check(matches(isDisplayed()));
//        onView(withId(R.id.rv_tag)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("汤粥")), click()));
//        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
//                .check(matches(withText("分类 - 汤粥")));

        onView(withId(R.id.rv_category)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("菜系")), click()));
        onView(allOf(withText("西餐"), isDescendantOfA(withId(R.id.rv_tag)))).check(matches(isDisplayed()));
        onView(withId(R.id.rv_tag)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("西餐")), click()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("分类 - 西餐")));
    }
}

package com.simoncherry.cookbookkotlin;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simoncherry.cookbookkotlin.ui.adapter.HistoryAdapter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/06/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CustomMatcher {

    /*
    * 匹配第一个符合匹配条件的项
    */
    static <T> Matcher<T> first(final Matcher<T> matcher) {
        return new BaseMatcher<T>() {
            boolean isFirst = true;

            @Override
            public boolean matches(final Object item) {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false;
                    return true;
                }

                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("should return first matching item");
            }
        };
    }

    /*
    * 匹配拥有指定数量Child的项
    */
    public static Matcher<View> withChildViewCount(final int count, final Matcher<View> childMatcher) {
        return new BoundedMatcher<View, ViewGroup>(ViewGroup.class) {
            @Override
            protected boolean matchesSafely(ViewGroup viewGroup) {
                int matchCount = 0;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    if (childMatcher.matches(viewGroup.getChildAt(i))) {
                        matchCount++;
                    }
                }

                return matchCount == count;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("ViewGroup with child-count=" + count + " and");
                childMatcher.describeTo(description);
            }
        };
    }

    /*
    * 获取匹配的TextView的文本内容
    */
    static String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    /*
    * 获取匹配的RecyclerView的Item数量
    */
    static int getRecyclerViewItemCount(final Matcher<View> matcher) {
        final int[] count = {0};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "getting RecyclerView Item Count";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView)view; //Save, because of check in getConstraints()
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                count[0] = adapter.getItemCount();
            }
        });
        return count[0];
    }

    /*
    * 匹配具有指定标题的近期浏览历史项
    */
    static Matcher<RecyclerView.ViewHolder> withHistoryTitle(final String title) {
        return new BoundedMatcher<RecyclerView.ViewHolder, HistoryAdapter.MyViewHolder>(HistoryAdapter.MyViewHolder.class) {
            @Override
            protected boolean matchesSafely(HistoryAdapter.MyViewHolder item) {
                return item.getTvName().getText().toString().equalsIgnoreCase(title);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("view holder with title: " + title);
            }
        };
    }

//    public static Matcher<Object> withCollapsibleToolbarTitle(final Matcher<String> textMatcher) {
//        return new BoundedMatcher<Object, CollapsingToolbarLayout>(CollapsingToolbarLayout.class) {
//            @Override public void describeTo(Description description) {
//                description.appendText("with toolbar title: "); textMatcher.describeTo(description);
//            }
//
//            @Override protected boolean matchesSafely(CollapsingToolbarLayout toolbarLayout) {
//                return textMatcher.matches(toolbarLayout.getTitle());
//            }
//        };
//    }

    /*
    * 获取匹配的CollapsingToolbarLayout的Title内容
    */
    static String getCollapsibleToolbarTitle(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(CollapsingToolbarLayout.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) view; //Save, because of check in getConstraints()
                CharSequence title = toolbarLayout.getTitle();
                if (title == null) {
                    title = "";
                }
                stringHolder[0] = title.toString();
            }
        });
        return stringHolder[0];
    }
}

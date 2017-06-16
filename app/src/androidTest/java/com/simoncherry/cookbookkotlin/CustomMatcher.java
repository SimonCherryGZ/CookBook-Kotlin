package com.simoncherry.cookbookkotlin;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
public class CustomMatcher {

    public static  <T> Matcher<T> first(final Matcher<T> matcher) {
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

    public static String getText(final Matcher<View> matcher) {
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

    public static int getRecyclerViewItemCount(final Matcher<View> matcher) {
        final int[] count = {0};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
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

    public static Matcher<RecyclerView.ViewHolder> withHistoryTitle(final String title) {
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
}

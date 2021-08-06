package algonquin.cst2335.inclassexamples_s21;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView( withId(R.id.pw) );
        appCompatEditText.perform(click());

        appCompatEditText.perform(replaceText("abcdef"));

        pressBack(); //hide the keyboard

        ViewInteraction materialButton = onView( withId(R.id.loginButton) );
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("No ABC string was found")));
    }

    @Test
    public void checkABCIsPresent( )
    {
        ViewInteraction appCompatEditText = onView( withId(R.id.pw) );
        appCompatEditText.perform(replaceText("ABCABCABC"));
//Find login button:
        ViewInteraction materialButton = onView( withId(R.id.loginButton) );
        materialButton.perform( click() );//will click the button

        //assertion:
        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("Your password has ABC")));
    }
    @Test
    public void checkABCIsPresent5( )
    {
        ViewInteraction appCompatEditText = onView( withId(R.id.pw) );
        appCompatEditText.perform(replaceText("ABCABCABC"));
//Find login button:
        ViewInteraction materialButton = onView( withId(R.id.loginButton) );
        materialButton.perform( click() );//will click the button

        //assertion:
        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("Your password has ABC")));
    }
    @Test
    public void checkABCIsPresent3( )
    {
        ViewInteraction appCompatEditText = onView( withId(R.id.pw) );
        appCompatEditText.perform(replaceText("ABCABCABC"));
//Find login button:
        ViewInteraction materialButton = onView( withId(R.id.loginButton) );
        materialButton.perform( click() );//will click the button

        //assertion:
        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("Your password has ABC")));
    }
    @Test
    public void checkABCIsPresent4( )
    {
        ViewInteraction appCompatEditText = onView( withId(R.id.pw) );
        appCompatEditText.perform(replaceText("ABCABCABC"));
//Find login button:
        ViewInteraction materialButton = onView( withId(R.id.loginButton) );
        materialButton.perform( click() );//will click the button

        //assertion:
        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("Your password has ABC")));
    }
    @Test
    public void checkABCIsPresent6( )
    {
        ViewInteraction appCompatEditText = onView( withId(R.id.pw) );
        appCompatEditText.perform(replaceText("ABCABCABC"));
//Find login button:
        ViewInteraction materialButton = onView( withId(R.id.loginButton) );
        materialButton.perform( click() );//will click the button

        //assertion:
        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("Your password has ABC")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

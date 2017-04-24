package ru.sinjvf.testtranslate;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.sinjvf.testtranslate.main.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * тестирование базового поведения первой стр (перевод)
 */
@RunWith(AndroidJUnit4.class)
public class UiTranslateTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(
            MainActivity.class);

    //проверяем переключение тулбара при пролистывании стр viewPager'a
    @Test
    public void checkToolbar(){
        android.content.res.Resources res = InstrumentationRegistry.getTargetContext().getResources();
        String title1 = res.getString(R.string.title_translate);
        String title2 = res.getString(R.string.title_history);
        String title3 = res.getString(R.string.title_favorites);
        ViewInteraction toolbarTitle = onView(allOf(
                isAssignableFrom(TextView.class),
                withParent(withId(R.id.toolbar))));
        ViewInteraction viewPager =  onView(withId(R.id.view_pager));
        //page 1
        viewPager.perform(swipeRight());
        toolbarTitle.check(matches(withText(title1)));
        //page 2
        viewPager.perform(swipeLeft());
        toolbarTitle.check(matches(withText(title2)));
        //page 1
        viewPager.perform(swipeRight());
        toolbarTitle.check(matches(withText(title1)));
        //page 3
        viewPager.perform(swipeLeft())
                .perform(swipeLeft());
        toolbarTitle.check(matches(withText(title3)));

    }

    //проверяем переключение языков
    @Test
    public void checkLangPair(){
        ViewInteraction spinnerFrom =  onView(withId(R.id.from_language));
        ViewInteraction spinnerTo =  onView(withId(R.id.to_language));
        //изначально языки - русский и англ
        spinnerFrom.check(matches(withSpinnerText(containsString("Русский"))));
        spinnerTo.check(matches(withSpinnerText(containsString("Английский"))));
        spinnerFrom.perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Английский"))).perform(click());
        spinnerFrom.check(matches(withSpinnerText(containsString("Английский"))));
        //после того как первый язык переключили на англ второй не может им оставаться
        spinnerTo.check(matches(withSpinnerText(not(containsString("Английский")))));
        spinnerTo.perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Датский"))).perform(click());
        spinnerTo.check(matches(withSpinnerText(containsString("Датский"))));
    }

    @Test
    public void checkClear(){
        ViewInteraction textToTr =  onView(withId(R.id.text_to_translate));
        ViewInteraction translation =  onView(withId(R.id.main_translation));
        ViewInteraction license =  onView(allOf(withId(R.id.license), withParent(withId(R.id.translation_container))));
        ViewInteraction language =  onView(withId(R.id.language));
        ViewInteraction star =  onView(withId(R.id.add_to_favorite));
        ViewInteraction clearButton =  onView(withId(R.id.clear_text));
        //проверяем, что при открытии не видны элементы дизайна с переводом
        textToTr.check(matches(withText("")));
        translation.check(matches(not(isDisplayed())));
        license.check(matches(not(isDisplayed())));
        star.check(matches(not(isDisplayed())));
        language.check(matches(not(isDisplayed())));
        // меняем текст и сразу же очищаем
        textToTr.perform(replaceText("text"));
        clearButton.perform(click());
        textToTr.check(matches(withText("")));
        translation.check(matches(not(isDisplayed())));
        license.check(matches(not(isDisplayed())));
        star.check(matches(not(isDisplayed())));
        language.check(matches(not(isDisplayed())));
    }

}

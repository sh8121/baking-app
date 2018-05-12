package com.example.android.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import android.support.test.espresso.contrib.RecyclerViewActions;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;

import com.example.android.bakingapp.recipelist.RecipeListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by lgpc on 2018-05-12.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListTest {
    private IdlingRegistry mIdlingRegistry;
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule
            = new ActivityTestRule<>(RecipeListActivity.class);

    @Before
    public void registerIdlingResource(){
        mIdlingRegistry = IdlingRegistry.getInstance();
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        mIdlingRegistry.register(mIdlingResource);
    }

    @Test
    public void clickSpecificRecipe(){
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void unregisterIdlingResource(){
        mIdlingRegistry.unregister(mIdlingResource);
    }
}

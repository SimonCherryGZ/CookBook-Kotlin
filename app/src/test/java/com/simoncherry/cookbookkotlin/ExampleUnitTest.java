package com.simoncherry.cookbookkotlin;

import com.simoncherry.cookbookkotlin.mvp.contract.RecipeContract;
import com.simoncherry.cookbookkotlin.mvp.presenter.RecipePresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Mock
    private RecipeContract.View mRecipeView;

    private RecipePresenter mRecipePresenter;

    @Before
    public void setupRecipePresenter() {
        MockitoAnnotations.initMocks(this);

        mRecipePresenter = new RecipePresenter();
        mRecipePresenter.attachView(mRecipeView);
    }

    @Test
    public void testQueryRecipe() {
        mRecipePresenter.queryRecipe("id", 1, 10);
    }
}
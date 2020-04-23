package com.example.diesiedler;

import com.example.diesiedler.presenter.Presenter;
import com.example.diesiedler.presenter.PresenterBuild;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        PresenterBuild presenterBuild = new PresenterBuild();
        String value = (String) presenterBuild.chooseAssetID("test");
        Assert.assertEquals("testString", value);
    }
}
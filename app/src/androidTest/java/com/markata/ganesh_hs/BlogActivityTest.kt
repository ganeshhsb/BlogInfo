package com.markata.ganesh_hs

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource
import com.markata.ganesh_hs.helpers.lazyActivityScenarioRule
import com.markata.ganesh_hs.common.offlineNetworkChecker
import com.markata.ganesh_hs.common.onlineNetworkChecker
import com.markata.ganesh_hs.di.dagger.DaggerAppComponent
import com.markata.ganesh_hs.ui.blog.BlogFragment
import com.markata.ganesh_hs.ui.blog.di.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@RunWith(AndroidJUnit4::class)
class BlogActivityTest {

    @get:Rule
    val rule =
        lazyActivityScenarioRule<BlogActivity>(launchActivity = false)

    @Before
    fun setUp() {
        //DaggerBlogComponent.builder().appComponent().build()
    }


    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testBlogInfoWhenOnline() {
//        unloadKoinModules(onlineNetworkChecker)
//        unloadKoinModules(offlineNetworkChecker)
//        loadKoinModules(onlineNetworkChecker)
        rule.launch()
        rule.getScenario().onActivity {
//            DaggerTestBlogComponent.builder().appComponent(DaggerAppComponent.factory().create(it.applicationContext))
//                .testBlogModule(TestBlogModule()).build().inject()
        }
        onView(withId(R.id.tenth_char)).check(matches(withText("WHITE_SPACE")))
        onView(withId(R.id.every_tenth_char)).check(matches(withText("     ")))
        onView(withId(R.id.unique_word_count)).check(matches(withText("0")))
    }

    @Test
    fun testBlogInfoWhenOffline() {
        rule.launch()
        rule.getScenario().onActivity {
            val blogFragment =it.supportFragmentManager.findFragmentById(R.id.main_fragment) as BlogFragment
            blogFragment.networkChecker = offlineNetworkChecker
        }
        onView(withId(R.id.network_error_title)).check(matches(isDisplayed()))
    }
}


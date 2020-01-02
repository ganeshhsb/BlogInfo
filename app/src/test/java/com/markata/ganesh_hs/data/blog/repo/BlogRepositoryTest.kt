package com.markata.ganesh_hs.data.blog.repo

import com.markata.ganesh_hs.RxImmediateSchedulerRule
import com.markata.ganesh_hs.common.RestfulAPIClient
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.Mockito.*
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.io.IOException

@RunWith(PowerMockRunner::class)
@PrepareForTest(RestfulAPIClient::class, ResponseBody::class)
class BlogRepositoryTest : AutoCloseKoinTest() {
    @Rule
    val rxScheduler = RxImmediateSchedulerRule()

    @Mock
    lateinit var restfulAPIClient: RestfulAPIClient

    private lateinit var candidateUnderTest: BlogRepository
    @Before
    fun setUp() {
        val testRepoModule = module {
            single { restfulAPIClient }
        }
        startKoin { modules(testRepoModule) }

        candidateUnderTest = BlogRepository()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test successfull emisssion`() {
        //preparation
        `when`(restfulAPIClient.fetchBlog()).thenReturn(
            Single.just(
                "Test123"
            )
        )

        // test
        val observer = TestObserver<Blog>()
        candidateUnderTest.fetchBlog()
            .subscribe(observer)
        observer.assertSubscribed()
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValue(Blog("Test123"))
    }

    @Test
    fun `test fail`() {
        // preparation

        `when`(restfulAPIClient.fetchBlog()).thenReturn(
            Single.error(IOException())
        )

        //test
        val observer = TestObserver<Blog>()
        candidateUnderTest.fetchBlog()
            .subscribe(observer)
        observer.assertSubscribed()
        observer.assertError(IOException::class.java)
        observer.assertNotComplete()
    }
}
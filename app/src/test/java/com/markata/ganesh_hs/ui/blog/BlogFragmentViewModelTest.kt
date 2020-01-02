package com.markata.ganesh_hs.ui.blog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.markata.ganesh_hs.RxImmediateSchedulerRule
import com.markata.ganesh_hs.common.EveryTenthCharNotFoundException
import com.markata.ganesh_hs.common.TenthCharNotFoundException
import com.markata.ganesh_hs.domain.blog.BlogTask
import com.markata.ganesh_hs.domain.blog.IBlogTask
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner


@RunWith(PowerMockRunner::class)
@PrepareForTest(BlogTask::class, Observer::class)
class BlogFragmentViewModelTest: AutoCloseKoinTest() {
    @Mock
    lateinit var task: BlogTask

    @Mock
    lateinit var blog10thCharLiveDataObserver: Observer<Result<Char>>

    @Mock
    lateinit var blogEvery10thCharLiveDataObserver: Observer<Result<String>>

    @Mock
    lateinit var blogWordCountObserver: Observer<Result<Int>>

    @Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxScheduler = RxImmediateSchedulerRule()

    private lateinit var candidateUnderTest: BlogFragmentViewModel
    @Before
    fun setUp() {
        val testTaskModule = module {
            single { task } bind IBlogTask::class
        }
        startKoin { modules(testTaskModule) }
        candidateUnderTest = BlogFragmentViewModel()
        candidateUnderTest.blog10thCharLiveData.observeForever(blog10thCharLiveDataObserver)
        candidateUnderTest.blogEvery10thCharLiveData.observeForever(blogEvery10thCharLiveDataObserver)
        candidateUnderTest.blogWordCount.observeForever(blogWordCountObserver)
    }

    @Test
    fun `test get10thCharacterInBlog success`() {
        val result: Single<Result<Char>> = Single.just(Result.success('p'))
        `when`(task.fetchBlogAndFind10thCharacter()).thenReturn(result)
        candidateUnderTest.get10thCharacterInBlog()
        assertTrue(candidateUnderTest.blog10thCharLiveData.hasObservers())
        assertEquals(
            Result.success('p'),
            candidateUnderTest.blog10thCharLiveData.value
        )
    }

    @Test
    fun `test get10thCharacterInBlog failed`() {
        val result: Single<Result<Char>> = Single.just(Result.failure(TenthCharNotFoundException()))
        `when`(task.fetchBlogAndFind10thCharacter()).thenReturn(result)
        candidateUnderTest.get10thCharacterInBlog()
        assertTrue(candidateUnderTest.blog10thCharLiveData.hasObservers())
        assertEquals(
            Result.failure<Char>(TenthCharNotFoundException()),
            candidateUnderTest.blog10thCharLiveData.value
        )
    }

    @Test
    fun `test getEvery10thCharacterInBlog success`() {
        val result: Single<Result<String>> = Single.just(Result.success("string"))
        `when`(task.fetchBlogAndFindEvery10thCharacter()).thenReturn(result)
        candidateUnderTest.getEvery10thCharacterInBlog()
        assertTrue(candidateUnderTest.blogEvery10thCharLiveData.hasObservers())
        assertEquals(
            Result.success("string"),
            candidateUnderTest.blogEvery10thCharLiveData.value
        )
    }

    @Test
    fun `test getEvery10thCharacterInBlog failed`() {
        val result: Single<Result<String>> = Single.just(Result.failure(EveryTenthCharNotFoundException()))
        `when`(task.fetchBlogAndFindEvery10thCharacter()).thenReturn(result)
        candidateUnderTest.getEvery10thCharacterInBlog()
        assertTrue(candidateUnderTest.blog10thCharLiveData.hasObservers())
        assertEquals(
            Result.failure<String>(EveryTenthCharNotFoundException()),
            candidateUnderTest.blogEvery10thCharLiveData.value
        )
    }

    @Test
    fun `test getWordCountInBlog success`() {
        val result: Single<Result<Int>> = Single.just(Result.success(5))
        `when`(task.fetchBlogAndCountWords()).thenReturn(result)
        candidateUnderTest.getWordCountInBlog()
        assertTrue(candidateUnderTest.blogWordCount.hasObservers())
        assertEquals(
            Result.success(5),
            candidateUnderTest.blogWordCount.value
        )
    }

    @Test
    fun `test getWordCountInBlog failed`() {
        val result: Single<Result<Int>> = Single.just(Result.failure(EveryTenthCharNotFoundException()))
        `when`(task.fetchBlogAndCountWords()).thenReturn(result)
        candidateUnderTest.getWordCountInBlog()
        assertTrue(candidateUnderTest.blog10thCharLiveData.hasObservers())
        assertEquals(
            Result.failure<Int>(EveryTenthCharNotFoundException()),
            candidateUnderTest.blogWordCount.value
        )
    }
}
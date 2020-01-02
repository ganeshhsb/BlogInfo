package com.markata.ganesh_hs.domain.blog

import com.markata.ganesh_hs.common.EveryTenthCharNotFoundException
import com.markata.ganesh_hs.common.TenthCharNotFoundException
import com.markata.ganesh_hs.data.blog.repo.Blog
import com.markata.ganesh_hs.data.blog.repo.BlogRepository
import com.markata.ganesh_hs.data.blog.repo.IBlogRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
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
@PrepareForTest(BlogRepository::class, ResponseBody::class)
class BlogTaskTest : AutoCloseKoinTest() {

    @Mock
    lateinit var repo: BlogRepository

    private lateinit var candidateUnderTest: IBlogTask
    @Before
    fun setUp() {
        val testTaskModule = module {
            single { repo } bind IBlogRepository::class
        }
        startKoin { modules(testTaskModule) }
        candidateUnderTest = BlogTask()
    }

    @After
    fun tearDown() {
    }

    // region 10thCharacter
    @Test
    fun `test Find the 10thCharacter`() {
        val testObserver = TestObserver<Result<Char>>()
        val tenthChar = 'p'
        `prepare BlogRepo for success case with given string`("qwertyuio" + tenthChar + "asdfghjklzxcvbnm")
        candidateUnderTest.fetchBlogAndFind10thCharacter().subscribe(testObserver)
        testObserver.assertValue(Result.success(tenthChar))

    }

    @Test
    fun `test Find the 10thCharacter in shorter string`() {
        val testObserver = TestObserver<Result<Char>>()
        `prepare BlogRepo for success case with given string`("qwertyuio")
        candidateUnderTest.fetchBlogAndFind10thCharacter().subscribe(testObserver)
        testObserver.assertValue(Result.failure(TenthCharNotFoundException()))
    }

    @Test
    fun `test Find the 10thCharacter in empty string`() {
        val testObserver = TestObserver<Result<Char>>()
        `prepare BlogRepo for success case with given string`("")
        candidateUnderTest.fetchBlogAndFind10thCharacter().subscribe(testObserver)
        testObserver.assertValue(Result.failure(TenthCharNotFoundException()))
    }
    // endregion

    // region Every10thCharacter
    @Test
    fun `test Every10thCharacter in 50 char string`() {
        val tenthChar = 'p'
        val templateString = "qwertyuio$tenthChar"
        var content = ""
        for (i in 1..5) {
            content += templateString
        }
        `prepare BlogRepo for success case with given string`(content)
        val expectedResult = "ppppp"
        val testObserver = TestObserver<Result<String>>()
        candidateUnderTest.fetchBlogAndFindEvery10thCharacter().subscribe(testObserver)

        testObserver.assertValue(Result.success(expectedResult))
    }

    @Test
    fun `test Every10thCharacter in 55 char string`() {
        val tenthChar = 'p'
        val templateString = "qwertyuio$tenthChar"
        var content = ""
        for (i in 1..5) {
            content += templateString
        }
        content += "12345"
        `prepare BlogRepo for success case with given string`(content)
        val expectedResult = "ppppp"
        val testObserver = TestObserver<Result<String>>()
        candidateUnderTest.fetchBlogAndFindEvery10thCharacter().subscribe(testObserver)

        testObserver.assertValue(Result.success(expectedResult))
    }

    @Test
    fun `test Every10thCharacter in 5 char string`() {
        val content = "12345"
        `prepare BlogRepo for success case with given string`(content)
        val expectedResult = ""
        val testObserver = TestObserver<Result<String>>()
        candidateUnderTest.fetchBlogAndFindEvery10thCharacter().subscribe(testObserver)

        testObserver.assertValue(Result.success(expectedResult))
    }

    @Test
    fun `test Every10thCharacter in empty string`() {
        val content = ""
        `prepare BlogRepo for success case with given string`(content)
        val testObserver = TestObserver<Result<String>>()
        candidateUnderTest.fetchBlogAndFindEvery10thCharacter().subscribe(testObserver)
        testObserver.assertValue(Result.failure(EveryTenthCharNotFoundException()))
    }
    // endregion

    // region WordCounter
    @Test
    fun `test WordCounter in 50 char string`(){
        val tenthChar = ' '
        val templateString = "qwertyuio$tenthChar"
        var content = ""
        for (i in 1..5) {
            content += templateString
        }
        `prepare BlogRepo for success case with given string`(content)
        val testObserver = TestObserver<Result<Int>>()
        candidateUnderTest.fetchBlogAndCountWords().subscribe(testObserver)

        testObserver.assertValue(Result.success(0))
    }

    @Test
    fun `test WordCounter in random string`(){
        val content = "   The quick brown fox    jumps over the lazy dog The    quick brown fox  jumps "
        `prepare BlogRepo for success case with given string`(content)
        val testObserver = TestObserver<Result<Int>>()
        candidateUnderTest.fetchBlogAndCountWords().subscribe(testObserver)

        testObserver.assertValue(Result.success(4))
    }
    // endregion

    // region prepare mock
    private fun `prepare BlogRepo for success case with given string`(content: String) {
        `when`(repo.fetchBlog()).thenReturn(
            Single.just(
                Blog(content)
            )
        )
    }
    // endregionM
}
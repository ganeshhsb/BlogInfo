package com.markata.ganesh_hs.ui.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource
import com.markata.ganesh_hs.common.EveryTenthCharNotFoundException
import com.markata.ganesh_hs.common.TenthCharNotFoundException
import com.markata.ganesh_hs.common.UniqueWordCountNotFoundException
import com.markata.ganesh_hs.domain.blog.BlogTask
import com.markata.ganesh_hs.domain.blog.IBlogTask
import com.markata.ganesh_hs.ui.blog.di.PerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import javax.inject.Inject

//@PerFragment
class BlogFragmentViewModel @Inject constructor(private val blogTask: IBlogTask) : ViewModel(),
    KoinComponent {
    private var _blog10thCharLiveData: MutableLiveData<Result<Char>> = MutableLiveData()
    private var _blogEvery10thCharLiveData: MutableLiveData<Result<String>> = MutableLiveData()
    private var _blogWordCount: MutableLiveData<Result<Int>> = MutableLiveData()

    val blog10thCharLiveData: LiveData<Result<Char>> = _blog10thCharLiveData
    val blogEvery10thCharLiveData: LiveData<Result<String>> = _blogEvery10thCharLiveData
    val blogWordCount: LiveData<Result<Int>> = _blogWordCount

    fun get10thCharacterInBlog(): LiveData<Result<Char>> {
        EspressoIdlingResource.increment()
        blogTask.fetchBlogAndFind10thCharacter().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Result<Char>>() {
                override fun onSuccess(tenthChar: Result<Char>) {
                    EspressoIdlingResource.decrement()
                    _blog10thCharLiveData.postValue(tenthChar.getOrNull() as Result<Char>)
                }

                override fun onError(e: Throwable) {
                    EspressoIdlingResource.decrement()
                    _blog10thCharLiveData.postValue(Result.failure(TenthCharNotFoundException()))
                }
            })
        return blog10thCharLiveData
    }

    fun getEvery10thCharacterInBlog(): LiveData<Result<String>> {
        EspressoIdlingResource.increment()
        blogTask.fetchBlogAndFindEvery10thCharacter()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Result<String>>() {
                override fun onSuccess(everyTenthChar: Result<String>) {
                    EspressoIdlingResource.decrement()
                    _blogEvery10thCharLiveData.postValue(everyTenthChar.getOrNull() as Result<String>)
                }

                override fun onError(e: Throwable) {
                    EspressoIdlingResource.decrement()
                    _blogEvery10thCharLiveData.postValue(
                        Result.failure(
                            EveryTenthCharNotFoundException()
                        )
                    )
                }
            })
        return blogEvery10thCharLiveData
    }

    fun getWordCountInBlog(): LiveData<Result<Int>> {
        EspressoIdlingResource.increment()
        blogTask.fetchBlogAndCountWords().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Result<Int>>() {
                override fun onSuccess(wordCount: Result<Int>) {
                    EspressoIdlingResource.decrement()
                    _blogWordCount.postValue(wordCount.getOrNull() as Result<Int>)
                }

                override fun onError(e: Throwable) {
                    EspressoIdlingResource.decrement()
                    _blogWordCount.postValue(Result.failure(UniqueWordCountNotFoundException()))
                }
            })
        return blogWordCount
    }
}
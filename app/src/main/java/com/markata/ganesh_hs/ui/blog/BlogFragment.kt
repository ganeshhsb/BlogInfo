package com.markata.ganesh_hs.ui.blog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.markata.ganesh_hs.R
import com.markata.ganesh_hs.common.AppApplication
import com.markata.ganesh_hs.common.INetworkChecker
import com.markata.ganesh_hs.common.NetworkChecker
import com.markata.ganesh_hs.common.state.Loadable
import com.markata.ganesh_hs.common.state.ViewState
import com.markata.ganesh_hs.common.viewModelProvider
import com.markata.ganesh_hs.di.dagger.AppModule
import com.markata.ganesh_hs.di.dagger.DaggerAppComponent
import com.markata.ganesh_hs.ui.blog.di.DaggerBlogComponent
import kotlinx.android.synthetic.main.fragment_blog.*
import javax.inject.Inject

class BlogFragment : Fragment(), Loadable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var networkChecker: INetworkChecker
    private lateinit var model: BlogFragmentViewModel //  by viewModel()
    // private val networkChecker: INetworkChecker by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blog, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val appComponent = (activity?.applicationContext as AppApplication).getAppComponent()
        DaggerBlogComponent.builder().appComponent(appComponent).build().inject(this)
        model = viewModelProvider(viewModelFactory)
        fetchBlogInfoIfDeviceOnline()
    }

    private fun fetchBlogInfoIfDeviceOnline() {
        if (networkChecker.isDeviceOnline(this.context)) {
            setState(this.context, ViewState.LOADING)
            fetchBlogInfo()
        } else {
            setState(
                this.context,
                ViewState.NETWORK_ERROR,
                View.OnClickListener { fetchBlogInfoIfDeviceOnline() })
        }
    }

    private fun fetchBlogInfo() {
        fetchTenthCharInBlog()
        fetchEveryTenthCharInBlog()
        fetchWordCount()
    }

    private fun fetchTenthCharInBlog() {
        model.get10thCharacterInBlog().observe(viewLifecycleOwner, Observer { result ->
            setState(this.context, ViewState.LOADED)
            tenth_char_info_container.visibility = View.VISIBLE
            result.fold({
                tenth_char.text = getTenthCharAsReadable(it)
            }, {
                tenth_char.text = getString(R.string.tenth_char_error)
            })
        })
    }

    private fun fetchEveryTenthCharInBlog() {
        model.getEvery10thCharacterInBlog().observe(viewLifecycleOwner, Observer { result ->
            setState(this.context, ViewState.LOADED)
            every_tenth_char_info_container.visibility = View.VISIBLE
            result.fold({ every10thString ->
                every_tenth_char.text = every10thString
            }, {
                every_tenth_char.text = getString(R.string.every_tenth_char_error)
            })
        })
    }

    private fun fetchWordCount() {
        model.getWordCountInBlog().observe(viewLifecycleOwner, Observer { result ->
            setState(this.context, ViewState.LOADED)
            word_count_info_container.visibility = View.VISIBLE
            result.fold({ count ->
                unique_word_count.text = count.toString()
            }, {
                unique_word_count.text = getString(R.string.word_count_error)
            })
        })
    }

    override fun getParentView(): ViewGroup? {
        return screen_state_holder
    }

    private fun getTenthCharAsReadable(char: Char): CharSequence? {
        return if (char.isWhitespace()) {
            getString(R.string.white_space)
        } else {
            char.toString()
        }
    }
}
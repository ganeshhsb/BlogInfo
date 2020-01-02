package com.markata.ganesh_hs.common.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.markata.ganesh_hs.R
import kotlinx.android.synthetic.main.component_error_screen.view.*

class StateScreen {
    companion object {
        const val VIEW_TAG = "STATE_SCREEN"
        fun addToView(
            context: Context?,
            state: ViewState,
            parentView: ViewGroup?,
            action: View.OnClickListener? = null,
            description: String? = null,
            title: String? = null
        ) {
            val layoutId = getLayoutId(state)
            layoutId?.let {
                val view = inflateView(context, parentView, it)
                when (state) {
                    ViewState.NETWORK_ERROR -> action?.let {
                        with(view.error_screen_action_button) {
                            visibility = View.VISIBLE
                            setOnClickListener(it)
                        }
                    }
                }
                view.tag = VIEW_TAG
                parentView?.addView(view)
            }
        }

        private fun getLayoutId(state: ViewState): Int? = when (state) {
            ViewState.LOADING -> R.layout.component_loading_screen
            ViewState.TRANSPARENT_LOADING -> R.layout.transparent_loading
            ViewState.NETWORK_ERROR -> R.layout.component_error_screen
            else -> null
        }

        private fun inflateView(
            context: Context?,
            parentView: ViewGroup?, @LayoutRes layoutId: Int
        ) = LayoutInflater.from(context).inflate(layoutId, parentView, false)
    }
}
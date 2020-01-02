package com.markata.ganesh_hs.common.state

import android.content.Context
import android.view.View
import android.view.ViewGroup

interface Loadable {
    fun getParentView(): ViewGroup?

    fun setState(
        context: Context?,
        state: ViewState,
        action: View.OnClickListener? = null,
        description: String? = null,
        title: String? = null
    ) {
        hideStateScreen()
        val parentView = getParentView()
        when (state) {
            ViewState.LOADED -> {
                parentView?.visibility = View.GONE
            }
            else -> {
                parentView?.visibility = View.VISIBLE
                StateScreen.addToView(context, state, parentView, action, description, title)
            }
        }
    }

    fun hideStateScreen() {
        getParentView()?.removeView(getParentView()?.findViewWithTag<View>(StateScreen.VIEW_TAG))
    }

}

enum class ViewState {
    TRANSPARENT_LOADING,
    LOADING,
    LOADED,
    EMPTY,
    NETWORK_ERROR
}
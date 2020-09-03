package com.fraud.apps.ui.components

import android.view.View
import androidx.core.widget.NestedScrollView
import me.everything.android.ui.overscroll.adapters.IOverScrollDecoratorAdapter

class NestedScrollViewOverScrollDecorAdapter(private val mView: NestedScrollView) : IOverScrollDecoratorAdapter {
    override fun getView(): View {
        return mView
    }

    override fun isInAbsoluteStart(): Boolean {
        return !mView.canScrollVertically(-1)
    }

    override fun isInAbsoluteEnd(): Boolean {
        return !mView.canScrollVertically(1)
    }

}
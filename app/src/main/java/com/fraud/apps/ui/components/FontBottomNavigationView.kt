package com.fraud.apps.ui.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.fraud.apps.R
import com.fraud.apps.utils.AndroidUtils
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.BaselineLayout

class FontBottomNavigationView : BottomNavigationView {


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    private var fontFace: Typeface? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val bottomMenu = getChildAt(0) as ViewGroup
        val bottomMenuChildCount: Int = bottomMenu.childCount
        var item: BottomNavigationItemView
        var itemTitle: View

        if (fontFace == null) {
            fontFace = Typeface.createFromAsset(context.assets, AndroidUtils.getString(R.string.font_path_poppins_medium))
        }

        for (i in 0 until bottomMenuChildCount) {
            try {
                item = bottomMenu.getChildAt(i) as BottomNavigationItemView
                itemTitle = item.getChildAt(1)
                ((itemTitle as BaselineLayout).getChildAt(0) as AppCompatTextView).typeface = fontFace
                (itemTitle.getChildAt(1) as AppCompatTextView).typeface = fontFace
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}
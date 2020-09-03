package com.fraud.apps.ui.components.loadingDialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import android.view.WindowManager
import com.fraud.apps.R

@Suppress("DEPRECATION")
class DialogLoading(context: Context) : Dialog(context, android.R.style.Theme_Holo_Light), OnGlobalLayoutListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
    }


    override fun onGlobalLayout() {
        val width = window?.decorView!!.width
        val height = window?.decorView!!.height
        window?.setLayout(width, height)
        val obs = window?.decorView!!.viewTreeObserver
        obs.removeOnGlobalLayoutListener(this)
    }

    init {
        window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        window?.setBackgroundDrawableResource(R.color.loading_transparent)

    }
}
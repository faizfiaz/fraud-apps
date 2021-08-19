package com.fitness.tracking.ui.components

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class PasswordInputText(context: Context?, attrs: AttributeSet?) : TextInputEditText(context!!, attrs) {
    init {
        this.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        this.transformationMethod = PasswordTransformationMethod()
    }
}
package com.kai.momentz.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.kai.momentz.R

class SingleSignOnButton : AppCompatButton {

    private lateinit var enabledLoginButton: Drawable
    private lateinit var disabledLoginButton: Drawable
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledLoginButton else disabledLoginButton
        setTextColor(txtColor)
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_dark)
        enabledLoginButton = ContextCompat.getDrawable(context, R.drawable.single_signon_button) as Drawable
    }

}
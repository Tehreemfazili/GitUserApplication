package com.example.gitusersapplication.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.Nullable
import androidx.viewpager.widget.ViewPager

class NonSwipeAbleViewPager(
    @get:JvmName("getContext_") private val context: Context,
    @Nullable private val attrs: AttributeSet?
) : ViewPager(context, attrs) {

    constructor(context: Context) : this(context, null)

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }

}
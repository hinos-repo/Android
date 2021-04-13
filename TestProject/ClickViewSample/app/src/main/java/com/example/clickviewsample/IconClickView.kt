package com.example.clickviewsample

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class IconClickView : LinearLayout
{
    private lateinit var m_tvIcon : TextView

    constructor(context: Context) : super(context)
    {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
        init()
        getAttrs(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
        getAttrs(attrs)
    }


    private fun init()
    {
        m_tvIcon = TextView(context).apply {
            isClickable = false
        }
        this.addView(m_tvIcon)
        this.orientation = VERTICAL
        this.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
    }

    private fun getAttrs(attrs : AttributeSet)
    {
        val arrTyped = this.context.obtainStyledAttributes(attrs, R.styleable.IconClickView)
        setTypeArray(arrTyped)
    }

    private fun setTypeArray(typedArray : TypedArray)
    {
        val paddingTop = typedArray.getDimension(R.styleable.IconClickView_android_paddingTop, 0f).toInt()
        val paddingBottom = typedArray.getDimension(R.styleable.IconClickView_android_paddingBottom, 0f).toInt()
        val paddingLeft = typedArray.getDimension(R.styleable.IconClickView_android_paddingStart, 0f).toInt()
        val paddingRight = typedArray.getDimension(R.styleable.IconClickView_android_paddingEnd, 0f).toInt()
        val iconResourceId = typedArray.getResourceId(R.styleable.IconClickView_iconSrc, R.mipmap.ic_launcher)
        val backgroundColor = typedArray.getColor(R.styleable.IconClickView_viewGroupColor, 0)
        val iconHeight = typedArray.getColor(R.styleable.IconClickView_iconHeight, 0)
        val iconWidth = typedArray.getColor(R.styleable.IconClickView_iconWidth, 0)


        this.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
        this.setBackgroundColor(backgroundColor)
        m_tvIcon.setBackgroundResource(iconResourceId)
        m_tvIcon.layoutParams = LayoutParams(iconWidth, iconHeight)
    }
}
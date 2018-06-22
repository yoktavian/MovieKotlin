package com.yoktavian.moviekotlin.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.yoktavian.moviekotlin.R
import kotlinx.android.synthetic.main.loading_view.view.*

class LoadingWidget(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.loading_view, this)
    }

    fun startLoading() {
        visibility = View.VISIBLE
        loading_view.visibility = View.VISIBLE
        reload_button.visibility = View.GONE
    }

    fun stopLoading() {
        visibility = View.GONE
        loading_view.visibility = View.GONE
        reload_button.visibility = View.GONE
    }

    fun stopLoadingWithError(errorMessage : String) {
        visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        reload_button.visibility = View.VISIBLE
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    fun setReloadClickListener(onClickListener: OnClickListener) {
        reload_button.setOnClickListener(onClickListener)
    }

    interface onClickListener : OnClickListener {
        override fun onClick(v: View?)
    }
}
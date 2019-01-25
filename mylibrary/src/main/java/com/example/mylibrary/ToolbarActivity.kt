package com.example.mylibrary

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class ToolbarActivity: AppCompatActivity(), iToolbar{

   protected var toolbar1: Toolbar? = null

    override fun toolbarToLoad(toolbar: Toolbar?) {
        toolbar1 = toolbar
        toolbar1?.let { setSupportActionBar(toolbar1) }
    }

    override fun enableHomeDisplay(value: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(value)
    }
}
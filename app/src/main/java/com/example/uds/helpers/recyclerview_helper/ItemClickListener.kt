package com.example.uds.helpers.recyclerview_helper

import android.view.View

interface ItemClickListener : View.OnClickListener {
    fun onItemClick(position: Int)
}
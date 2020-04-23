package com.example.uds.models

data class Schedule(
    val title: String,
    val description: String,
    val author: String,
    var isExpanded: Boolean
)
package com.example.uds.models

data class Schedule(
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    val author: String,
    var isExpanded: Boolean
)
package com.example.uds.models

data class Schedule(
    var id: String = "",
    val title: String = "",
    val shortDescription: String = "",
    val fullDescription: String = "",
    val author: String = "",
    var isExpanded: Boolean = false
)
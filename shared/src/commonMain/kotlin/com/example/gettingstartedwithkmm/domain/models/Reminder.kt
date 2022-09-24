package com.example.gettingstartedwithkmm.domain.models

data class Reminder(
    val id: String,
    val title: String,
    val isCompleted: Boolean = false,
)
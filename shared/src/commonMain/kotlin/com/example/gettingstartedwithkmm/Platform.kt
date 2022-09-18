package com.example.gettingstartedwithkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
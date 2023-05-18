package com.example.translatorkmmed

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
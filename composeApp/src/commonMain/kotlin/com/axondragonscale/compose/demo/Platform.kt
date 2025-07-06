package com.axondragonscale.compose.demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
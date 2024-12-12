package org.crevolika.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
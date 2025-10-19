package com.example.helloworld.debug

enum class LogCategory(val emoji: String) {
    GENERAL("📝"),
    AUTH("🔐"),
    NETWORK("🌐"),
    DATABASE("💾"),
    UI("🎨");

    fun getDisplayName(): String = name.lowercase().replaceFirstChar { it.uppercase() }
}

package com.example.helloworld.debug

import android.graphics.Color

enum class LogLevel(val emoji: String, val color: Int) {
    DEBUG("🔍", Color.parseColor("#808080")),      // Gray
    INFO("ℹ️", Color.parseColor("#2196F3")),       // Blue
    WARNING("⚠️", Color.parseColor("#FF9800")),    // Orange
    ERROR("❌", Color.parseColor("#F44336")),      // Red
    CRITICAL("🔥", Color.parseColor("#9C27B0"));   // Purple

    fun getDisplayName(): String = name.lowercase().replaceFirstChar { it.uppercase() }
}

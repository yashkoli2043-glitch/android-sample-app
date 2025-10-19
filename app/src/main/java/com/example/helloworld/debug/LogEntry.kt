package com.example.helloworld.debug

import java.text.SimpleDateFormat
import java.util.*

data class LogEntry(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Date = Date(),
    val level: LogLevel,
    val message: String,
    val location: String,
    val category: LogCategory? = null
) {
    fun getFormattedTime(): String {
        val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
        return formatter.format(timestamp)
    }

    fun getFormattedLog(): String {
        val categoryPrefix = category?.let { "[${it.name}] " } ?: ""
        return "[${getFormattedTime()}] $categoryPrefix[${level.name}] $location - $message"
    }
}

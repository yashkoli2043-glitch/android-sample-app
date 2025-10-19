package com.example.helloworld.debug

import android.util.Log

object DebugLogger {
    private val logs = mutableListOf<LogEntry>()
    private val maxLogCount = 1000
    private val listeners = mutableListOf<() -> Unit>()

    fun log(
        message: String,
        level: LogLevel = LogLevel.INFO,
        category: LogCategory? = null,
        fileName: String = "",
        functionName: String = "",
        lineNumber: Int = 0
    ) {
        val location = if (fileName.isNotEmpty()) {
            "$fileName:$functionName:$lineNumber"
        } else {
            "Unknown"
        }

        val entry = LogEntry(
            timestamp = java.util.Date(),
            level = level,
            message = message,
            location = location,
            category = category
        )

        synchronized(logs) {
            logs.add(entry)

            // Keep only the last 1000 entries
            if (logs.size > maxLogCount) {
                val excessCount = logs.size - maxLogCount
                repeat(excessCount) {
                    logs.removeAt(0)
                }
            }
        }

        // Notify listeners of new log
        notifyListeners()

        // Also print to Android logcat
        val categoryPrefix = category?.let { "[${it.emoji} ${it.name}] " } ?: ""
        val logMessage = "$categoryPrefix[${level.emoji} ${level.name}] $location - $message"

        when (level) {
            LogLevel.DEBUG -> Log.d("DebugLogger", logMessage)
            LogLevel.INFO -> Log.i("DebugLogger", logMessage)
            LogLevel.WARNING -> Log.w("DebugLogger", logMessage)
            LogLevel.ERROR -> Log.e("DebugLogger", logMessage)
            LogLevel.CRITICAL -> Log.wtf("DebugLogger", logMessage)
        }
    }

    fun getLogs(): List<LogEntry> {
        synchronized(logs) {
            return logs.toList()
        }
    }

    fun clearLogs() {
        synchronized(logs) {
            logs.clear()
        }
        notifyListeners()
    }

    fun exportLogs(): String {
        synchronized(logs) {
            return logs.joinToString("\n") { it.getFormattedLog() }
        }
    }

    fun addListener(listener: () -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: () -> Unit) {
        listeners.remove(listener)
    }

    private fun notifyListeners() {
        listeners.forEach { it() }
    }
}

// Convenience functions for easier logging
// These use reflection to get the calling location
private fun getCallerInfo(): Triple<String, String, Int> {
    val stackTrace = Thread.currentThread().stackTrace
    // Find the first stack element that's not from this file or Thread class
    val callerElement = stackTrace.firstOrNull {
        !it.className.contains("DebugLogger") &&
        !it.className.contains("java.lang.Thread")
    }

    return if (callerElement != null) {
        val fileName = callerElement.fileName ?: "Unknown"
        val methodName = callerElement.methodName
        val lineNumber = callerElement.lineNumber
        Triple(fileName, methodName, lineNumber)
    } else {
        Triple("Unknown", "Unknown", 0)
    }
}

fun DLog(message: String, category: LogCategory? = null) {
    if (com.example.helloworld.BuildConfig.DEBUG) {
        val (fileName, methodName, lineNumber) = getCallerInfo()
        DebugLogger.log(message, LogLevel.DEBUG, category, fileName, methodName, lineNumber)
    }
}

fun ILog(message: String, category: LogCategory? = null) {
    val (fileName, methodName, lineNumber) = getCallerInfo()
    DebugLogger.log(message, LogLevel.INFO, category, fileName, methodName, lineNumber)
}

fun WLog(message: String, category: LogCategory? = null) {
    val (fileName, methodName, lineNumber) = getCallerInfo()
    DebugLogger.log(message, LogLevel.WARNING, category, fileName, methodName, lineNumber)
}

fun ELog(message: String, category: LogCategory? = null) {
    val (fileName, methodName, lineNumber) = getCallerInfo()
    DebugLogger.log(message, LogLevel.ERROR, category, fileName, methodName, lineNumber)
}

fun CLog(message: String, category: LogCategory? = null) {
    val (fileName, methodName, lineNumber) = getCallerInfo()
    DebugLogger.log(message, LogLevel.CRITICAL, category, fileName, methodName, lineNumber)
}

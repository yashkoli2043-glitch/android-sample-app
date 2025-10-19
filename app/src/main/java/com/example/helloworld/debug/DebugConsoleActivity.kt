package com.example.helloworld.debug

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText

class DebugConsoleActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var searchEditText: TextInputEditText
    private lateinit var logsRecyclerView: RecyclerView
    private lateinit var clearButton: MaterialButton
    private lateinit var exportButton: MaterialButton

    private lateinit var filterAll: Chip
    private lateinit var filterDebug: Chip
    private lateinit var filterInfo: Chip
    private lateinit var filterWarning: Chip
    private lateinit var filterError: Chip
    private lateinit var filterCritical: Chip

    private val logAdapter = LogAdapter()
    private var selectedLevel: LogLevel? = null
    private var searchText = ""

    private val logUpdateListener: () -> Unit = {
        runOnUiThread {
            updateLogs()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_debug_console)

        initViews()
        setupToolbar()
        setupRecyclerView()
        setupSearch()
        setupFilters()
        setupButtons()

        // Add listener for log updates
        DebugLogger.addListener(logUpdateListener)

        updateLogs()
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugLogger.removeListener(logUpdateListener)
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        searchEditText = findViewById(R.id.searchEditText)
        logsRecyclerView = findViewById(R.id.logsRecyclerView)
        clearButton = findViewById(R.id.clearButton)
        exportButton = findViewById(R.id.exportButton)

        filterAll = findViewById(R.id.filterAll)
        filterDebug = findViewById(R.id.filterDebug)
        filterInfo = findViewById(R.id.filterInfo)
        filterWarning = findViewById(R.id.filterWarning)
        filterError = findViewById(R.id.filterError)
        filterCritical = findViewById(R.id.filterCritical)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        logsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DebugConsoleActivity).apply {
                reverseLayout = true
                stackFromEnd = true
            }
            adapter = logAdapter
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                searchText = s?.toString() ?: ""
                updateLogs()
            }
        })
    }

    private fun setupFilters() {
        val filterChips = listOf(
            filterAll to null,
            filterDebug to LogLevel.DEBUG,
            filterInfo to LogLevel.INFO,
            filterWarning to LogLevel.WARNING,
            filterError to LogLevel.ERROR,
            filterCritical to LogLevel.CRITICAL
        )

        filterChips.forEach { (chip, level) ->
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedLevel = level
                    // Uncheck other chips
                    filterChips.forEach { (otherChip, _) ->
                        if (otherChip != chip) {
                            otherChip.isChecked = false
                        }
                    }
                    updateLogs()
                }
            }
        }
    }

    private fun setupButtons() {
        clearButton.setOnClickListener {
            DebugLogger.clearLogs()
            logAdapter.clearExpandedItems()
            updateLogs()
        }

        exportButton.setOnClickListener {
            exportLogs()
        }
    }

    private fun updateLogs() {
        val allLogs = DebugLogger.getLogs()

        // Update filter counts
        filterAll.text = "All (${allLogs.size})"
        filterDebug.text = "🔍 Debug (${allLogs.count { it.level == LogLevel.DEBUG }})"
        filterInfo.text = "ℹ️ Info (${allLogs.count { it.level == LogLevel.INFO }})"
        filterWarning.text = "⚠️ Warning (${allLogs.count { it.level == LogLevel.WARNING }})"
        filterError.text = "❌ Error (${allLogs.count { it.level == LogLevel.ERROR }})"
        filterCritical.text = "🔥 Critical (${allLogs.count { it.level == LogLevel.CRITICAL }})"

        // Apply filters
        var filteredLogs = allLogs

        // Filter by level
        selectedLevel?.let { level ->
            filteredLogs = filteredLogs.filter { it.level == level }
        }

        // Filter by search text
        if (searchText.isNotEmpty()) {
            filteredLogs = filteredLogs.filter {
                it.message.contains(searchText, ignoreCase = true) ||
                        it.location.contains(searchText, ignoreCase = true)
            }
        }

        // Submit to adapter (reversed to show newest first)
        logAdapter.submitList(filteredLogs.reversed())
    }

    private fun exportLogs() {
        val logsText = DebugLogger.exportLogs()

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, logsText)
            putExtra(Intent.EXTRA_SUBJECT, "Debug Logs")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Export Logs")
        startActivity(shareIntent)
    }
}

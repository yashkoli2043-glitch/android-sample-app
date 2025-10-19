package com.example.helloworld.debug

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.R

class LogAdapter : ListAdapter<LogEntry, LogAdapter.LogViewHolder>(LogDiffCallback()) {

    private val expandedItems = mutableSetOf<String>()

    class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emojiTextView: TextView = itemView.findViewById(R.id.emojiTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        val expandedMessageTextView: TextView = itemView.findViewById(R.id.expandedMessageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_log_entry, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val entry = getItem(position)
        val isExpanded = expandedItems.contains(entry.id)

        holder.emojiTextView.text = entry.level.emoji
        holder.timestampTextView.text = entry.getFormattedTime()
        holder.locationTextView.text = entry.location
        holder.messageTextView.setTextColor(entry.level.color)

        // Category
        if (entry.category != null) {
            holder.categoryTextView.text = entry.category.emoji
            holder.categoryTextView.visibility = View.VISIBLE
        } else {
            holder.categoryTextView.visibility = View.GONE
        }

        // Message handling for expanded/collapsed state
        if (isExpanded) {
            holder.messageTextView.visibility = View.GONE
            holder.expandedMessageTextView.visibility = View.VISIBLE
            holder.expandedMessageTextView.text = entry.message
            holder.expandedMessageTextView.setTextColor(entry.level.color)
        } else {
            holder.messageTextView.visibility = View.VISIBLE
            holder.expandedMessageTextView.visibility = View.GONE
            holder.messageTextView.text = entry.message
        }

        // Click to expand/collapse
        holder.itemView.setOnClickListener {
            if (expandedItems.contains(entry.id)) {
                expandedItems.remove(entry.id)
            } else {
                expandedItems.add(entry.id)
            }
            notifyItemChanged(position)
        }

        // Long press to copy and output to logcat
        holder.itemView.setOnLongClickListener {
            val formattedLog = entry.getFormattedLog()

            // Copy to clipboard
            val clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Log Entry", formattedLog)
            clipboard.setPrimaryClip(clip)

            // Output to logcat for access from Claude Code / adb logcat
            // Make it highly visible with separators
            Log.i("DebugConsole-Selected", "")
            Log.i("DebugConsole-Selected", "═══════════════════════════════════════════════════════════════")
            Log.i("DebugConsole-Selected", "CLAUDE_CODE_LOG_START")
            Log.i("DebugConsole-Selected", formattedLog)
            Log.i("DebugConsole-Selected", "CLAUDE_CODE_LOG_END")
            Log.i("DebugConsole-Selected", "═══════════════════════════════════════════════════════════════")
            Log.i("DebugConsole-Selected", "")

            Toast.makeText(it.context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
            true
        }
    }

    fun clearExpandedItems() {
        expandedItems.clear()
    }

    class LogDiffCallback : DiffUtil.ItemCallback<LogEntry>() {
        override fun areItemsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean {
            return oldItem == newItem
        }
    }
}

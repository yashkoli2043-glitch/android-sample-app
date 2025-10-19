Fetch the most recent log entry from the in-app debugger and analyze it.

This command retrieves the last log entry that was copied via long-press in the DebugConsoleActivity, then provides intelligent analysis including root cause and potential fixes.

Steps:
1. Use adb logcat to fetch recent logs with the tag "DebugConsole-Selected"
2. Look for the log entry between "CLAUDE_CODE_LOG_START" and "CLAUDE_CODE_LOG_END" markers
3. Extract and display the log content clearly
4. Analyze the log entry to identify:
   - Root cause of the issue
   - Potential fix or solution
   - Code location to investigate (if applicable)
   - Related files or components that might be involved
5. If no recent log is found, inform the user to long-press a log entry in the app first

Analysis Guidelines:
- For NETWORK errors: Check URL validity, endpoint existence, network permissions, HTTP methods
- For UI errors: Check view lifecycle, threading issues, resource availability
- For CRASH errors: Examine stack traces, null pointer issues, type mismatches
- For GENERAL errors: Look at context and surrounding code logic
- Provide specific, actionable recommendations with code examples when possible

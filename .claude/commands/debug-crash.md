Analyze a crash from the Luciq dashboard and suggest root cause and potential fixes.

## Usage

Provide a Luciq crash URL or crash details (slug, mode, crash number).

## Process

1. **Parse Crash Information**
   - If a URL is provided, extract the application slug, mode, and crash number
   - Luciq URLs typically follow the pattern: `https://dashboard.luciq.ai/crashes/{slug}/{mode}/{number}`
   - If individual parameters are provided, use them directly

2. **Fetch Crash Details**
   - Use the `mcp__secure-api__crash_details` tool with the extracted parameters
   - Retrieve comprehensive crash information including:
     - Stack trace
     - Device information
     - OS version
     - App version
     - Crash frequency and occurrence patterns
     - Affected user count

3. **Analyze the Crash**
   - Examine the stack trace to identify the failing code path
   - Review the exception type and error message
   - Identify the specific file and line number where the crash occurred
   - Consider device/OS patterns if multiple instances exist
   - Check for known Android/Kotlin patterns or common pitfalls

4. **Suggest Root Cause**
   - Provide a clear explanation of why the crash is happening
   - Reference specific code locations using the format `file_path:line_number`
   - Explain the technical cause (e.g., null pointer, index out of bounds, lifecycle issue)
   - Identify contributing factors (e.g., specific device types, OS versions, user actions)

5. **Recommend Fixes**
   - Suggest specific code changes to resolve the crash
   - Provide code snippets or patches when applicable
   - Recommend defensive coding practices to prevent similar crashes
   - Suggest additional logging or error handling if needed
   - Prioritize fixes based on crash frequency and user impact

## Output Format

Present findings in a structured format:
- **Crash Summary**: Brief overview with crash type and frequency
- **Root Cause**: Technical explanation of why the crash occurs
- **Affected Code**: Specific file(s) and line number(s)
- **Recommended Fix**: Step-by-step solution with code examples
- **Prevention**: Best practices to avoid similar issues

## Notes

- If the crash URL format is unclear, ask the user to provide the slug, mode, and crash number separately
- Focus on actionable insights that can be immediately implemented
- Consider Android-specific issues (lifecycle, threading, memory management)
- Check if the crash is related to code in this repository or external libraries

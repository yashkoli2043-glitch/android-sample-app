package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.helloworld.debug.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Log app launch
        ILog("App launched successfully", LogCategory.GENERAL)
        DLog("MainActivity created", LogCategory.UI)

        setupDebugConsole()
        makeDelayedNetworkCall()
    }

    private fun setupDebugConsole() {
        val debugFab = findViewById<FloatingActionButton>(R.id.debugFab)
        debugFab.setOnClickListener {
            val intent = Intent(this, DebugConsoleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun makeDelayedNetworkCall() {
        lifecycleScope.launch {
            // Wait 2 seconds after launch
            delay(2000)

            ILog("Initiating network request...", LogCategory.NETWORK)

            withContext(Dispatchers.IO) {
                try {
                    val request = Request.Builder()
                        .url("https://httpbin.org/gett")
                        .build()

                    httpClient.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) {
                            ELog(
                                "Network request failed: HTTP ${response.code} - ${response.message}",
                                LogCategory.NETWORK
                            )
                            ELog("URL: ${request.url}", LogCategory.NETWORK)
                        } else {
                            ILog("Network request succeeded: HTTP ${response.code}", LogCategory.NETWORK)
                        }
                    }
                } catch (e: IOException) {
                    ELog("Network request exception: ${e.message}", LogCategory.NETWORK)
                }
            }
        }
    }
}

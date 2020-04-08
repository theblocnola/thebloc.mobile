package com.nolansherman.spotifly

import android.content.Intent
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.LoginActivity.REQUEST_CODE
import io.flutter.plugin.common.PluginRegistry
import com.spotify.sdk.android.auth.AuthorizationResponse.Type.*;

class SpotiflyActivityResultListener: PluginRegistry.ActivityResultListener {
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?): Boolean {
        // Check if result comes from the correct activity
        if (requestCode === REQUEST_CODE) {
            val response: AuthorizationResponse = AuthorizationClient.getResponse(resultCode, intent)
            when (response.getType()) {
                TOKEN -> {
                }
                ERROR -> {
                }
                else -> {
                }
            }
        }
    }
}
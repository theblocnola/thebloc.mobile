package com.nolansherman.spotifly

import android.app.Activity
import androidx.annotation.NonNull
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AccountsQueryParameters.CLIENT_ID
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse
import io.flutter.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar


/** SpotiflyPlugin */
public class SpotiflyPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {

  private var activity: Activity? = null

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().dartExecutor, "spotifly")
    channel.setMethodCallHandler(SpotiflyPlugin());
  }

  override fun onDetachedFromActivity() {
    TODO("Not yet implemented")
  }

  override fun onReattachedToActivityForConfigChanges(p0: ActivityPluginBinding) {
    TODO("Not yet implemented")
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    this.activity = binding.activity;
  }

  override fun onDetachedFromActivityForConfigChanges() {
    TODO("Not yet implemented")
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "spotifly")
      channel.setMethodCallHandler(SpotiflyPlugin())
    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "authorize") {
      authorize(call, result)
    }  else if (call.method == "authorizeAppRemote") {
      authorizeAppRemote(call, result)
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
  }

  // Request code will be used to verify if result comes from the login activity. Can be set to any integer.
  private val REQUEST_CODE = 1337
  private val REDIRECT_URI = "yourcustomprotocol://callback"

  fun authorize(@NonNull call: MethodCall, @NonNull result: Result){
    val builder: AuthorizationRequest.Builder = AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)

    builder.setScopes(arrayOf("streaming"))
    val request: AuthorizationRequest = builder.build()

    AuthorizationClient.openLoginActivity(this.activity, REQUEST_CODE, request)
  }

  fun authorizeAppRemote(@NonNull call: MethodCall, @NonNull result: Result){
    val connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()

    SpotifyAppRemote.connect(this.activity, connectionParams,
            object : Connector.ConnectionListener {
              override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                var mSpotifyAppRemote = spotifyAppRemote
                Log.d("Spotifly", "Connected to App Remote")

                // Now you can start interacting with App Remote
              }

              override fun onFailure(throwable: Throwable) {
                Log.e("Spotifly", throwable.message.toString())

                // Something went wrong when attempting to connect! Handle errors here
              }
            })
  }


}




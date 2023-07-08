package com.example.telecom_manager_intent

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.util.ViewUtils.getActivity


class MethodCallHandlerImpl(context: Context): MethodCallHandler {

    private var context: Context?

    companion object {
        private const val REQUEST_CODE_SET_DEFAULT_DIALER = 123
    }

    init {
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        val actionArg = call.argument<String>("action")
        val action = actionArg?.let { getAction(it) }
        when(call.method) {
            "start" -> {
                val intent = Intent(action)
                intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getActivity(this.context!!)?.packageName)
                startActivity(this.context!!, intent, Bundle.EMPTY)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getAction(action: String) : String {
       return when(action) {
            "action_change_default_dialer" -> TelecomManager.ACTION_CHANGE_DEFAULT_DIALER
           else -> ""
        }
    }
}
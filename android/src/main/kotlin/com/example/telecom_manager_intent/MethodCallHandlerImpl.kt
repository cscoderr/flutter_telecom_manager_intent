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


internal class MethodCallHandlerImpl(context: Context): MethodCallHandler {

    private var context: Context?

    companion object {
        private const val REQUEST_CODE_SET_DEFAULT_DIALER = 123
    }

    init {
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        try {
            when(call.method) {
                "defaultDailer" -> defaultDialer()
                else -> result.notImplemented()
            }
        } catch (e: Exception) {
            result.error(logTag, null, e.toString())
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun defaultDialer() {
        val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
        intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getActivity(this.context!!)?.packageName)
        startActivity(this.context!!, intent, Bundle.EMPTY)
    }
}
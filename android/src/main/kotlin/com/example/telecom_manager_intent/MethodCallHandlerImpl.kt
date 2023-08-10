package com.example.telecom_manager_intent

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.TELECOM_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import androidx.annotation.RequiresApi
import android.widget.Toast;
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.util.ViewUtils.getActivity
import androidx.core.app.ActivityCompat.startActivityForResult;


internal class MethodCallHandlerImpl(context: Context, activity: Activity?): MethodCallHandler {

    private var context: Context?
    private var activity: Activity?
    private val logTag: String = "FlutterTelecomManagerIntent"

    companion object {
        private const val REQUEST_CODE_SET_DEFAULT_DIALER = 123
    }

    init {
        this.activity = activity
        this.context = context
    }

    fun setActivity(act: Activity?) {
        this.activity = act
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        try {
            when(call.method) {
                "defaultDialer" -> defaultDialer()
                else -> result.notImplemented()
            }
        } catch (e: Exception) {
            result.error(logTag, null, e.toString())
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun defaultDialer() {
//        val telecomManager = getActivity(this.context!!)!!.getSystemService(TELECOM_SERVICE) as TelecomManager
        val packageName = this.context!!.getPackageName();
        Toast.makeText(this.context!!, "here", Toast.LENGTH_SHORT).show()
//        val isAlreadyDefaultDialer = packageName == telecomManager.defaultDialerPackage
//        if (isAlreadyDefaultDialer) return
        val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
        Toast.makeText(this.context!!, packageName, Toast.LENGTH_SHORT).show()
        startActivityForResult(this.activity!!, intent, MethodCallHandlerImpl.REQUEST_CODE_SET_DEFAULT_DIALER, null)
        Toast.makeText(this.context!!, "end", Toast.LENGTH_SHORT).show()
    }
}
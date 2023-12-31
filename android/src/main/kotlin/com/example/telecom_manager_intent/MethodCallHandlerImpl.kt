package com.example.telecom_manager_intent

import android.annotation.TargetApi
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.app.role.RoleManager
import android.content.Context
import android.content.Context.TELECOM_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telecom.TelecomManager
import android.util.Log
import androidx.annotation.RequiresApi
import android.widget.Toast;
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.util.ViewUtils.getActivity
import androidx.core.app.ActivityCompat.startActivityForResult;


class MethodCallHandlerImpl(context: Context, activity: Activity?, methodChannel: MethodChannel): MethodCallHandler {

    private var context: Context?
    private var activity: Activity?
    private var methodChannel: MethodChannel
    private val logTag: String = "FlutterTelecomManagerIntent"

    companion object {
        private const val REQUEST_CODE_SET_DEFAULT_DIALER = 123
        private const val REQUEST_CODE_SET_DEFAULT_SMS = 1
    }

    init {
        this.activity = activity
        this.context = context
        this.methodChannel = methodChannel
    }

    fun setActivity(act: Activity?) {
        this.activity = act
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        try {
            when(call.method) {
                "defaultDialer" -> {
                    defaultDialer()
                    result.success(null)
                }
                "isDefaultDialer" -> {
                   val response =  isDefaultDialer()
                    result.success(response)
                }
                "defaultSms" -> {
                    defaultSms()
                    result.success(null)
                }
                "isDefaultSms" -> {
                    val response = isDefaultSms()
                    result.success(response)
                }
                else -> result.notImplemented()
            }
        } catch (e: Exception) {
            result.error(logTag, null, e.toString())
        }

    }

    private fun getPackageName(): String {
        val packageName = this.activity!!.getPackageName();
        return packageName
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isDefaultDialer() : Boolean {
        val telecomManager = this.activity!!.getSystemService(TELECOM_SERVICE) as TelecomManager
        val packageName = getPackageName()
        val isAlreadyDefaultDialer = packageName == telecomManager.defaultDialerPackage
        return isAlreadyDefaultDialer
    }

    fun defaultDialer() {
        if (isDefaultDialer()) return
        val packageName = getPackageName()
        val packageManager = this.activity!!.getPackageManager();

        Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
            .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
            .apply {
            if(resolveActivity(packageManager) != null) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val roleManager: RoleManager? = activity!!.getSystemService(RoleManager::class.java)
                    if (roleManager?.isRoleAvailable(RoleManager.ROLE_DIALER) == true) {
                        activity!!.startActivityForResult(
                            roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER),
                            REQUEST_CODE_SET_DEFAULT_DIALER
                        )
                    } else {
                        Log.w("TelecomManagerIntent", "An error occur");
                    }
                } else {
                    activity!!.startActivityForResult(this, REQUEST_CODE_SET_DEFAULT_DIALER)
                }
            } else {
                // result.error()
                Log.w("TelecomManagerIntent", "No Intent available to handle action");
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun isDefaultSms(): Boolean {
        val telephony = Telephony.Sms.getDefaultSmsPackage(context)
        val packageName = getPackageName()
        return packageName == telephony
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun defaultSms() {
        val packageName = getPackageName()
        val packageManager = this.activity!!.getPackageManager();
        Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            .putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
            .apply {
            if(resolveActivity(packageManager) != null) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val roleManager: RoleManager? = activity!!.getSystemService(RoleManager::class.java)
                    if (roleManager?.isRoleAvailable(RoleManager.ROLE_SMS) == true) {
                        if (roleManager?.isRoleHeld(RoleManager.ROLE_SMS) == true) {
                            Log.w("TelecomManagerIntent", "No Permission");
                            TODO("Handle permission later")
                        } else {
                            activity!!.startActivityForResult(
                                roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS),
                                REQUEST_CODE_SET_DEFAULT_SMS
                            )
                        }
                    } else {
                        Log.w("TelecomManagerIntent", "An error occur");
                    }
                } else {
                    if(!isDefaultSms()) {
                        activity!!.startActivityForResult(this, REQUEST_CODE_SET_DEFAULT_SMS)
                    }
                }
            } else {
                // result.error()
                Log.w("TelecomManagerIntent", "No Intent available to handle action");
            }
        }
    }
}
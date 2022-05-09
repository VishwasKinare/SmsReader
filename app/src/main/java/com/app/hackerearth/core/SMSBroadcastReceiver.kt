package com.app.hackerearth.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.app.hackerearth.R
import com.app.hackerearth.core.Utility.getOtp
import com.app.hackerearth.presentation.activities.HomeActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SMSBroadcastReceiver: BroadcastReceiver() {

    private lateinit var mBuilder: NotificationCompat.Builder

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status : Status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message: String = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                    val otp = message.getOtp()
                    if (otp.isNotEmpty())
                        context.sendNotification(otp)
                }
                CommonStatusCodes.TIMEOUT -> {}
            }
        }
    }

    private fun Context.sendNotification(otp: String){
        val intentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, HomeActivity::class.java),
            intentFlag
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id = getString(R.string.app_name)
            val name = "OTP"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(id, name, importance)
            mChannel.description = otp
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            val mNotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.createNotificationChannel(mChannel)
        }
        mBuilder = NotificationCompat.Builder(this, getString(R.string.app_name))
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            .setContentTitle("OTP")
            .setContentText(otp)
            .setContentIntent(contentIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL).priority = NotificationCompat.PRIORITY_HIGH
        NotificationManagerCompat.from(this).apply {
            notify(120, mBuilder.build())
        }
    }
}
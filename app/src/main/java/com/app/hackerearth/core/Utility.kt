package com.app.hackerearth.core

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.app.hackerearth.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.regex.Matcher
import java.util.regex.Pattern


object Utility {

    fun Context.getPermissionDialog(message: String): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.permission_required))
            .setMessage(message)
    }

    fun Long.getDate(): String {
        return android.text.format.DateUtils.getRelativeTimeSpanString(this).toString()
    }

    fun String.getOtp(): String{
        val pattern: Pattern = Pattern.compile("(?<!\\d)\\d{6}(?!\\d)")
        val matcher: Matcher = pattern.matcher(this)
        var otp = ""
        if (matcher.find()) {
            otp = matcher.group(0) ?: ""
        }
        return otp
    }

    fun Context.copyToClipBoard(otp: String){
        val myClipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val myClip = ClipData.newPlainText("Otp", otp)
        myClipboard.setPrimaryClip(myClip)
        Toast.makeText(this, getString(R.string.otp_copied), Toast.LENGTH_SHORT).show()
    }
}
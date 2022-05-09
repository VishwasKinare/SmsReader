package com.app.hackerearth.data.local

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import com.app.hackerearth.core.Resource
import com.app.hackerearth.core.Status
import com.app.hackerearth.core.Utility.getOtp
import com.app.hackerearth.domain.model.Messages
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsHandler @Inject constructor(val context: Context) {

    fun fetchSms(): Resource<ArrayList<Messages>>{
        val message = ArrayList<Messages>()
        val inboxURI: Uri = Uri.parse("content://sms/inbox")
        val reqCols = arrayOf("_id", Telephony.Sms.Inbox.ADDRESS, Telephony.Sms.Inbox.BODY, Telephony.Sms.DATE)
        val cr = context.contentResolver
        val cursor: Cursor? = cr.query(inboxURI, reqCols, null, null, null)
        while (cursor != null && cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
            val smsDate = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
            val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
            val otp = body.getOtp()
            message.add(Messages(id, body, address, smsDate, otp))
        }
        cursor?.close()
        return Resource(Status.SUCCESS, message)
    }
}
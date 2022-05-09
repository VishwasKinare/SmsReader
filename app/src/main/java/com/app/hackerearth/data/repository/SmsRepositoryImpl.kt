package com.app.hackerearth.data.repository

import com.app.hackerearth.core.Resource
import com.app.hackerearth.data.local.SmsHandler
import com.app.hackerearth.domain.model.Messages
import com.app.hackerearth.domain.repository.SmsRepository
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(
    private val smsHandler: SmsHandler
): SmsRepository {

    override fun getSmsList(): Resource<ArrayList<Messages>> {
        return smsHandler.fetchSms()
    }
}
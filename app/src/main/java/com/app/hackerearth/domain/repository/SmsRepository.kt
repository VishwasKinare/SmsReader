package com.app.hackerearth.domain.repository

import com.app.hackerearth.core.Resource
import com.app.hackerearth.domain.model.Messages

interface SmsRepository {
    fun getSmsList(): Resource<ArrayList<Messages>>
}
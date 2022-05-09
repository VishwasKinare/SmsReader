package com.app.hackerearth.domain.model

import java.io.Serializable

data class Messages(
    val id: String,
    val body: String,
    val address: String,
    val date: Long,
    val otp: String
): Serializable

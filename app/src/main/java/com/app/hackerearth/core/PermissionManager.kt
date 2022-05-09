package com.app.hackerearth.core

import android.Manifest
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class PermissionManager @Inject constructor(
    registry: ActivityResultRegistry
) {

    companion object {
        private const val SMS_PERMISSIONS = "READ_SMS_PERMISSIONS"
    }

    private val _smsPermission = MutableLiveData<Boolean>()
    val smsPermission: LiveData<Boolean> = _smsPermission

    private val getSmsPermission = registry.register(SMS_PERMISSIONS,
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        var permGiven = true
        it.entries.forEach { element ->
            if (element.value != true)
                permGiven = false
        }
        _smsPermission.postValue(permGiven)
    }

    fun readSmsPermission(){
        getSmsPermission.launch(arrayOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS
        ))
    }
}
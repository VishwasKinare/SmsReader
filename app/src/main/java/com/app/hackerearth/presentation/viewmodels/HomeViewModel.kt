package com.app.hackerearth.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hackerearth.R
import com.app.hackerearth.core.ApiState
import com.app.hackerearth.core.Status
import com.app.hackerearth.domain.model.Messages
import com.app.hackerearth.domain.usecase.SMSListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val smsListUseCase: SMSListUseCase
): ViewModel(){

    private val _smsList = MutableLiveData<ApiState<ArrayList<Messages>>>()
    val smsList: LiveData<ApiState<ArrayList<Messages>>> = _smsList

    fun getSmsList(){
        smsListUseCase().onEach { result ->
            when(result.status){
                Status.SUCCESS ->{
                    _smsList.postValue(ApiState(data = result.data))
                }
                Status.ERROR -> {
                    _smsList.postValue(ApiState(error = result.message ?: R.string.no_text_messages))
                }
                Status.LOADING -> {
                    _smsList.postValue(ApiState(isLoading = true))
                }
            }
        }.launchIn(viewModelScope + Dispatchers.IO)
    }
}
package com.app.hackerearth.domain.usecase

import com.app.hackerearth.R
import com.app.hackerearth.core.Resource
import com.app.hackerearth.domain.model.Messages
import com.app.hackerearth.domain.repository.SmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SMSListUseCase @Inject constructor(
    private val repository: SmsRepository
)  {

    operator fun invoke(): Flow<Resource<ArrayList<Messages>>> = flow {
        try {
            emit(Resource.loading())
            val response =  repository.getSmsList()
            response.data?.let {
                emit(Resource.success(it))
            } ?:  emit(Resource.error(R.string.no_text_messages, null))
        } catch (e: IOException){
            emit(Resource.error(e.localizedMessage ?: R.string.no_text_messages, null))
        }
    }
}
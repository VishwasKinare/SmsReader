package com.app.hackerearth.di

import com.app.hackerearth.data.local.SmsHandler
import com.app.hackerearth.data.repository.SmsRepositoryImpl
import com.app.hackerearth.domain.repository.SmsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun getSmsRepository(
        smsHandler: SmsHandler
    ): SmsRepository = SmsRepositoryImpl(smsHandler)
}
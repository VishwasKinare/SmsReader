package com.app.hackerearth.di

import com.app.hackerearth.domain.repository.SmsRepository
import com.app.hackerearth.domain.usecase.SMSListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun getSmsListUseCase(
        repo: SmsRepository,
    ) = SMSListUseCase(repo)
}
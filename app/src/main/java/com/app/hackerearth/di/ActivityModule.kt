package com.app.hackerearth.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.app.hackerearth.data.local.SmsHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    @ActivityScoped
    fun getPermissionManager(
        @ActivityContext activity: Context
    ) = (activity as? AppCompatActivity)?.activityResultRegistry
        ?: throw IllegalArgumentException("You must use AppCompatActivity")
}
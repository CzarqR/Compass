package com.myniprojects.compass.di

import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule
{
    @Singleton
    @Provides
    fun provideGeocoder(
        @ApplicationContext context: Context
    ): Geocoder = Geocoder(context, Locale.getDefault())
}
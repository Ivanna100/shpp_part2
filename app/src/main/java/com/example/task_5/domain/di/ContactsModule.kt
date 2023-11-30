package com.example.task_5.domain.di

import com.example.task_5.domain.network.ContactsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContactsModule {

    @Provides
    @Singleton
    fun providesContactsApiService(retrofit: Retrofit) : ContactsApiService {
        return retrofit.create(ContactsApiService::class.java)
    }
}
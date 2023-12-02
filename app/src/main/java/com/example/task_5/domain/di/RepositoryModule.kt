package com.example.task_5.domain.di

import com.example.task_5.data.repository.AccountRepositoryImpl
import com.example.task_5.data.repository.ContactsRepositoryImpl
import com.example.task_5.domain.repository.AccountRepository
import com.example.task_5.domain.repository.ContactsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
    @InstallIn(ActivityComponent::class)
    abstract class RepositoryModule {

        @Binds
        abstract fun bindAccountRepository(
            accountRepositoryImpl: AccountRepositoryImpl
        ): AccountRepository


        @Binds
        abstract fun bindContactsRepository(
            contactsRepositoryImpl: ContactsRepositoryImpl
        ): ContactsRepository
    }
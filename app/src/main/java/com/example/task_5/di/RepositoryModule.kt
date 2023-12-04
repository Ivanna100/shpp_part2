package com.example.task_5.di

import com.example.task_5.data.repository.AccountRepositoryImpl
import com.example.task_5.data.repository.ContactsRepositoryImpl
import com.example.task_5.domain.repository.AccountRepository
import com.example.task_5.domain.repository.ContactsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAccountRepository(
        accountRepository: AccountRepositoryImpl
    ): AccountRepository


    @Binds
    @Singleton
    abstract fun bindContactsRepository(
        contactsRepository: ContactsRepositoryImpl
    ): ContactsRepository
}
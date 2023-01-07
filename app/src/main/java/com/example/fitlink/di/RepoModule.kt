package com.example.fitlink.di

import com.example.fitlink.repository.FireStoreRepoImpl
import com.example.fitlink.repository.FireStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideFireStoreRepo(
        repo: FireStoreRepoImpl
    ): FireStoreRepository
}
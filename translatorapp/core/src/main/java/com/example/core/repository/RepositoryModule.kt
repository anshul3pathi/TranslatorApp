package com.example.core.repository

import com.example.core.TranslationDataSource
import com.example.core.local.LocalDataSource
import com.example.core.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class RemoteImplementation

@Qualifier
annotation class LocalImplementation

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @RemoteImplementation
    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(impl: RemoteDataSource): TranslationDataSource

    @LocalImplementation
    @Singleton
    @Binds
    abstract fun bindLocalDataSource(impl: LocalDataSource): TranslationDataSource

    @Singleton
    @Binds
    abstract fun bindTranslationRepository(impl: TranslationRepository): TranslationRepo

}
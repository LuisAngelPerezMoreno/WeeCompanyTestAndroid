package com.example.weecompanyjctest.di

import com.example.weecompanyjctest.provider.CountryProvider
import com.example.weecompanyjctest.repository.CountryRepository
import com.example.weecompanyjctest.repository.ICountryRepository
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
    fun providerCountryRepository(provider: CountryProvider): ICountryRepository =
        CountryRepository(provider)
}
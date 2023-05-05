package com.challenge.code_base_sdk.di

import com.challenge.code_base_sdk.rest.CharacterRepository
import com.challenge.code_base_sdk.rest.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * This is a Dagger Hilt module called RepositoryModule.
 * The @InstallIn annotation specifies that this module should be installed in the SingletonComponent,
 * which means that it will provide a single instance of the dependencies it provides throughout the application.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * The @Binds annotation is used to bind the CharacterRepository interface to its implementation CharacterRepositoryImpl.
     * This means that any time a CharacterRepository is requested, Dagger Hilt will provide an instance of CharacterRepositoryImpl.
     * The abstract keyword is used to mark the class as abstract, which means that it cannot be instantiated directly.
     */
    @Binds
    abstract fun providesRepository(
        characterRepository: CharacterRepositoryImpl
    ): CharacterRepository
}
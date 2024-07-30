package com.pedromoura.chatdevspace.di

import com.pedromoura.chatdevspace.data.datasource.ApiService
import com.pedromoura.chatdevspace.data.repository.LoginRepositoryImpl
import com.pedromoura.chatdevspace.domain.repository.LoginRepository
import com.pedromoura.chatdevspace.domain.usecase.GetLoginUseCase
import com.pedromoura.chatdevspace.domain.usecase.GetLoginUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(): ApiService {
        return ApiService()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: LoginRepository): GetLoginUseCase {
        return GetLoginUseCaseImpl(loginRepository)
    }
}
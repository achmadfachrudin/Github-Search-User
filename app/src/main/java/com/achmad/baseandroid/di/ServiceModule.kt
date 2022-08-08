package com.achmad.baseandroid.di

import com.achmad.baseandroid.service.GithubRemote
import com.achmad.baseandroid.service.GithubRepository
import com.achmad.baseandroid.service.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideGithubService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubRemote(pokedexService: GithubService): GithubRemote {
        return GithubRemote(pokedexService)
    }

    @Provides
    fun provideGithubRepository(
        GithubRemote: GithubRemote
    ): GithubRepository {
        return GithubRepository(GithubRemote)
    }
}

package ru.potemkin.vknewsclient.di

import android.content.Context
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.potemkin.vknewsclient.data.network.ApiFactory
import ru.potemkin.vknewsclient.data.network.ApiService
import ru.potemkin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.potemkin.vknewsclient.domain.repository.NewsFeedRepository

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideVkStorage(
            context: Context
        ): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }

}
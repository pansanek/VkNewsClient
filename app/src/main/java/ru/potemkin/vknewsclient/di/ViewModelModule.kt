package ru.potemkin.vknewsclient.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.potemkin.vknewsclient.presentation.comments.CommentsViewModel
import ru.potemkin.vknewsclient.presentation.main.MainViewModel
import ru.potemkin.vknewsclient.presentation.news.NewsFeedViewModel

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}
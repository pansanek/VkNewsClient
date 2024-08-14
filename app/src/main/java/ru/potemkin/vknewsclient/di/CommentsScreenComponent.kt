package ru.potemkin.vknewsclient.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.potemkin.vknewsclient.domain.entity.FeedPost
import ru.potemkin.vknewsclient.presentation.ViewModelFactory


@Subcomponent(
    modules = [
        CommentsViewModelModule::class
    ]
)
interface CommentsScreenComponent {


    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}
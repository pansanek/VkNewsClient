package ru.potemkin.vknewsclient.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.potemkin.vknewsclient.domain.entity.FeedPost
import ru.potemkin.vknewsclient.presentation.main.MainActivity

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context,
            @BindsInstance feedPost: FeedPost,
        ): ApplicationComponent
    }
}
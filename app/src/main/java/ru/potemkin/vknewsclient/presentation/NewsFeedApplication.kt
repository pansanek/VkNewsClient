package ru.potemkin.vknewsclient.presentation

import android.app.Application
import ru.potemkin.vknewsclient.di.ApplicationComponent

class NewsFeedApplication : Application() {

    val component: ApplicationComponent by lazy {
        TODO()
    }
}
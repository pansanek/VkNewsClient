package ru.potemkin.vknewsclient.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.potemkin.vknewsclient.di.ApplicationComponent

class NewsFeedApplication : Application() {

    val component: ApplicationComponent by lazy {
        TODO()
    }
}


@Composable
fun getApplicationComponent():ApplicationComponent{
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}
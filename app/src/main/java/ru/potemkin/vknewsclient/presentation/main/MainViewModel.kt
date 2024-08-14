package ru.potemkin.vknewsclient.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.potemkin.vknewsclient.domain.usecases.CheckAuthStateUseCase
import ru.potemkin.vknewsclient.domain.usecases.GetAuthStateFlowUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getAuthStateFlowUseCase = GetAuthStateFlowUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateFlowUseCase()


    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}
package ru.potemkin.vknewsclient.domain.usecases

import ru.potemkin.vknewsclient.domain.entity.AuthState
import ru.potemkin.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateFlowUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}
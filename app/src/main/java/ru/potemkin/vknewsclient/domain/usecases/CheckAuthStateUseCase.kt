package ru.potemkin.vknewsclient.domain.usecases

import ru.potemkin.vknewsclient.domain.repository.NewsFeedRepository

class CheckAuthStateUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke() {
        repository.checkAuthState()
    }
}
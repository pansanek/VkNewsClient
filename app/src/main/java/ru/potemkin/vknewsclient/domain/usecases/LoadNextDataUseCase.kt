package ru.potemkin.vknewsclient.domain.usecases

import ru.potemkin.vknewsclient.domain.repository.NewsFeedRepository

class LoadNextDataUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke() {
        repository.loadNextData()
    }
}
package ru.potemkin.vknewsclient.domain.usecases

import ru.potemkin.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class LoadNextDataUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke() {
        repository.loadNextData()
    }
}
package ru.potemkin.vknewsclient.domain.usecases

import ru.potemkin.vknewsclient.domain.entity.FeedPost
import ru.potemkin.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetRecommendationsUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<List<FeedPost>> {
        return repository.getRecommendations()
    }
}
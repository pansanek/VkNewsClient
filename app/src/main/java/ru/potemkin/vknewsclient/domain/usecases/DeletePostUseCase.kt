package ru.potemkin.vknewsclient.domain.usecases

import ru.potemkin.vknewsclient.domain.entity.FeedPost
import ru.potemkin.vknewsclient.domain.repository.NewsFeedRepository

class DeletePostUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(feedPost: FeedPost) {
        repository.deletePost(feedPost)
    }
}
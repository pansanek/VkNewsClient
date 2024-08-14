package ru.potemkin.vknewsclient.domain.usecases

import ru.potemkin.vknewsclient.domain.entity.FeedPost
import ru.potemkin.vknewsclient.domain.entity.PostComment
import ru.potemkin.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> {
        return repository.getComments(feedPost)
    }
}
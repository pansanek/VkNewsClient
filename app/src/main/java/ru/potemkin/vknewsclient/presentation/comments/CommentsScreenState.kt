package ru.potemkin.vknewsclient.presentation.comments

import ru.potemkin.vknewsclient.domain.entity.FeedPost
import ru.potemkin.vknewsclient.domain.entity.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}

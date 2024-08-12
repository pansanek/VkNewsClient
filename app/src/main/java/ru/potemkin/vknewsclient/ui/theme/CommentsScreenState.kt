package ru.potemkin.vknewsclient.ui.theme

import ru.potemkin.vknewsclient.domain.FeedPost
import ru.potemkin.vknewsclient.domain.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}

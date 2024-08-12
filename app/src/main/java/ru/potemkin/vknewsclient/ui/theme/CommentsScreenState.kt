package com.sumin.vknewsclient.ui.theme

import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}

package ru.potemkin.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import ru.potemkin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.potemkin.vknewsclient.domain.entity.FeedPost

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : ViewModel() {
    private val repository = NewsFeedRepositoryImpl(application)


    val screenState = repository.getComments(feedPost)
        .map { CommentsScreenState.Comments(
            feedPost,
            it
        ) }

}

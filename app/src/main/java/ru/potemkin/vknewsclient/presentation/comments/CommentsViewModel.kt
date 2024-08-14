package ru.potemkin.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import ru.potemkin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.potemkin.vknewsclient.domain.entity.FeedPost
import ru.potemkin.vknewsclient.domain.usecases.GetCommentsUseCase
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {


    val screenState = getCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost,
                it
            )
        }

}

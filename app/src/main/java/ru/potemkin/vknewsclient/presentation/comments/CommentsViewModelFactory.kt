package ru.potemkin.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.vknewsclient.domain.entity.FeedPost

class CommentsViewModelFactory(
    private val feedPost: FeedPost,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost,application) as T
    }
}

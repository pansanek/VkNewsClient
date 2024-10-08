package ru.potemkin.vknewsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.potemkin.vknewsclient.domain.entity.FeedPost
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.potemkin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.potemkin.vknewsclient.domain.usecases.ChangeLikeStatusUseCase
import ru.potemkin.vknewsclient.domain.usecases.DeletePostUseCase
import ru.potemkin.vknewsclient.domain.usecases.GetRecommendationsUseCase
import ru.potemkin.vknewsclient.domain.usecases.LoadNextDataUseCase
import ru.potemkin.vknewsclient.extensions.mergeWith
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {


    private val recommendationsFlow = getRecommendationsUseCase()


    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedViewModel", "Exception caught")
    }

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()
    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }


    fun remove(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost)
        }
    }
}


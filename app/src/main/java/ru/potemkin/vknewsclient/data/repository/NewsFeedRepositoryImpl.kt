package ru.potemkin.vknewsclient.data.repository

import android.app.Application
import android.util.Log
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.potemkin.vknewsclient.data.mapper.NewsFeedMapper
import ru.potemkin.vknewsclient.data.network.ApiFactory
import ru.potemkin.vknewsclient.domain.entity.FeedPost
import ru.potemkin.vknewsclient.domain.entity.PostComment
import ru.potemkin.vknewsclient.domain.entity.StatisticItem
import ru.potemkin.vknewsclient.domain.entity.StatisticType
import ru.potemkin.vknewsclient.extensions.mergeWith
import ru.potemkin.vknewsclient.domain.entity.AuthState
import ru.potemkin.vknewsclient.domain.repository.NewsFeedRepository

class NewsFeedRepositoryImpl(application: Application) : NewsFeedRepository {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token
        get() = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val checkAuthStateEvent = MutableSharedFlow<Unit>(replay = 1)

    val authStateFlow = flow{
        checkAuthStateEvent.collect{
            checkAuthStateEvent.emit(Unit)
            Log.d("TOKENTOKEN",token?.accessToken.toString())
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            val authState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        AuthState.Initial
    )

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()
    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) apiService.loadRecommendations(getAccessToken())
            else apiService.loadRecommendations(getAccessToken(), startFrom)

            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry (2){
        delay(RETRY_TIMEOUT)
        true
    }.catch {

    }

    override suspend fun checkAuthState(){
        checkAuthStateEvent.emit(Unit)
    }

    val recommendations: StateFlow<List<FeedPost>> = loadedListFlow.mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    private fun getAccessToken(): String {
        Log.d("TOKENTOKEN", token?.accessToken.toString())
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            getAccessToken(),
            feedPost.communityId,
            feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override fun getRecommendations(): StateFlow<List<FeedPost>>  = recommendations

    override fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>> = flow {
        val comments = apiService.getComments(
            getAccessToken(),
            feedPost.communityId,
            feedPost.id
        )
        emit(mapper.mapResponseToComments(comments))
    }.retry{
        delay(RETRY_TIMEOUT)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshedListFlow.emit(feedPosts)
    }


    companion object{

        private const val RETRY_TIMEOUT = 300L
    }
}
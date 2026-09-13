package com.besha.shinobihub.features.detail.presentaion.viewmodel

import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.mvi.CommonViewState
import com.besha.shinobihub.appcore.mvi.MVIBaseViewModel
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse
import com.besha.shinobihub.features.detail.data.model.mark.MarkResponse
import com.besha.shinobihub.features.detail.data.model.review.Review
import com.besha.shinobihub.features.detail.data.model.status.AccountStatesResponse
import com.besha.shinobihub.features.detail.data.model.video.VideoItem
import com.besha.shinobihub.features.detail.domain.model.DetailMediaItem
import com.besha.shinobihub.features.detail.domain.usecase.GetDetailMovieUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetDetailPersonUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetDetailTvUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetMovieAccountStateUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetMovieCreditsUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetMovieReviewsUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetMovieVideoUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetMoviesSimilarUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetPeopleCreditsUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetTvAccountStateUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetTvCreditsUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetTvReviewsUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetTvSimilarUseCase
import com.besha.shinobihub.features.detail.domain.usecase.GetTvVideoUseCase
import com.besha.shinobihub.features.detail.domain.usecase.ToggleFavoriteUseCase
import com.besha.shinobihub.features.detail.domain.usecase.ToggleWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getDetailTvUseCase: GetDetailTvUseCase,
    private val getDetailPersonUseCase: GetDetailPersonUseCase,
    private val getMovieVideoUseCase: GetMovieVideoUseCase,
    private val getTvVideoUseCase: GetTvVideoUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getTvCreditsUseCase: GetTvCreditsUseCase,
    private val getPeopleCreditsUseCase: GetPeopleCreditsUseCase,
    private val getMoviesSimilarUseCase: GetMoviesSimilarUseCase,
    private val getTvSimilarUseCase: GetTvSimilarUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getTvReviewsUseCase: GetTvReviewsUseCase,
    private val getMovieAccountStateUseCase: GetMovieAccountStateUseCase,
    private val getTvAccountStateUseCase: GetTvAccountStateUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase,
    private val sessionManager: SessionManager
) : MVIBaseViewModel<DetailActions, DetailResults, DetailViewState>() {

    override val defaultViewState: DetailViewState
        get() = DetailViewState()

    override fun handleAction(action: DetailActions): Flow<DetailResults> = flow {

        when (action) {

            is DetailActions.SwitchTab ->{
                emit(DetailResults.SwitchTab(CommonViewState(data = action.tab)))
            }

            is DetailActions.GetDetailMovie -> {
                handleGetDetailMovie(this){
                    getDetailMovieUseCase(action.movieId)
                }
            }
            is DetailActions.GetDetailTv ->{
                handleGetDetailMovie(this){
                    getDetailTvUseCase(action.seriesId)
                }
            }
            is DetailActions.GetDetailPerson->{
                handleGetDetailMovie(this){
                    getDetailPersonUseCase(action.personId)
                }
            }

            is DetailActions.GetMovieVideo ->{
                handleGetVideo(this){
                    getMovieVideoUseCase(action.movieId)
                }
            }

            is DetailActions.GetTvVideo ->{
                handleGetVideo(this){
                    getTvVideoUseCase(action.seriesId)
                }
            }

            is DetailActions.GetMovieCredits->{

                handleGetCredits(this){
                    getMovieCreditsUseCase(action.movieId)
                }
            }

            is DetailActions.GetTvCredits->{
                handleGetCredits(this){
                    getTvCreditsUseCase(action.seriesId)
                }
            }
            is DetailActions.GetPeopleCredits->{
                handleGetPeopleCredits(this){
                    getPeopleCreditsUseCase(action.personId)
                }
            }

            is DetailActions.GetMovieSimilar->{
                handleGetSimilar(this){
                    getMoviesSimilarUseCase(action.movieId)
                }
            }

            is DetailActions.GetTvSimilar->{
                handleGetSimilar(this){
                    getTvSimilarUseCase(action.seriesId)
                }
            }
            is DetailActions.GetMovieReviews -> {

                handleGetReviews(this){
                    getMovieReviewsUseCase(action.movieId)
                }


            }

            is DetailActions.GetTvReviews ->{
                handleGetReviews(this){
                    getTvReviewsUseCase(action.seriesId)
                }

            }

            is DetailActions.GetSessionId->{
                handleGetSessionId(this)
            }
            is DetailActions.GetMovieAccountState -> {
                handleGetAccountState(this) {
                    val sessionId = sessionManager.getSessionId().firstOrNull()
                    if (sessionId != null)
                        getMovieAccountStateUseCase(action.movieId, sessionId)
                    else
                        DataState.Error(Throwable("Session ID not found"))
                }
            }

            is DetailActions.GetTvAccountState -> {
                handleGetAccountState(this) {
                    val sessionId = sessionManager.getSessionId().firstOrNull()
                    if (sessionId != null)
                        getTvAccountStateUseCase(action.tvId, sessionId)
                    else
                        DataState.Error(Throwable("Session ID not found"))
                }
            }

            is DetailActions.ToggleFavorite -> {
                handleToggleFavorite(this) {
                    val sessionId = sessionManager.getSessionId().firstOrNull()
                    val accountId = sessionManager.getAccountId().firstOrNull()
                    if (sessionId != null && accountId != null)
                        toggleFavoriteUseCase(accountId, action.markRequest, sessionId)
                    else
                        DataState.Error(Throwable("Missing session or account ID"))
                }
            }

            is DetailActions.ToggleWatchList -> {
                handleToggleWatchlist(this) {
                    val sessionId = sessionManager.getSessionId().firstOrNull()
                    val accountId = sessionManager.getAccountId().firstOrNull()
                    if (sessionId != null && accountId != null)
                        toggleWatchlistUseCase(accountId, action.markRequest, sessionId)
                    else
                        DataState.Error(Throwable("Missing session or account ID"))
                }
            }
            DetailActions.ResetToggleCode -> {
                emit(DetailResults.ToggleCodeResult(0))
            }

        }

    }

    private suspend fun handleGetAccountState(
        flowCollector: FlowCollector<DetailResults>,
        getState: suspend () -> DataState<AccountStatesResponse>
    ) {
        flowCollector.emit(DetailResults.Loading(true))

        when (val result = getState()) {
            is DataState.Success -> {
                val data = result.data
                flowCollector.emit(DetailResults.AccountStateLoaded(
                    favorite = data.favorite ,
                    watchlist = data.watchlist
                ))
            }
            is DataState.Error -> {
                flowCollector.emit(DetailResults.AccountStateLoaded(false, false))
            }
            is DataState.Empty -> {
                flowCollector.emit(DetailResults.AccountStateLoaded(false, false))
            }
            else -> {}
        }

        flowCollector.emit(DetailResults.Loading(false))
    }
    private suspend fun handleToggleFavorite(
        flowCollector: FlowCollector<DetailResults>,
        toggle: suspend () -> DataState<MarkResponse>
    ) {

        flowCollector.emit(DetailResults.ToggleFavoriteResult(CommonViewState(isLoading = true), 0))
        when (val result = toggle() ) {
            is DataState.Success -> {
                val code = result.data.status_code
                when (code) {
                    1 -> flowCollector.emit(DetailResults.ToggleFavoriteResult(CommonViewState(data = true), code))  // added
                    13 -> flowCollector.emit(DetailResults.ToggleFavoriteResult(CommonViewState(data = false), code)) // removed
                    else -> flowCollector.emit(DetailResults.ToggleCodeResult( code ))
                }
            }
            is DataState.Error -> flowCollector.emit(DetailResults.ToggleCodeResult( -1 ))
            else -> {}
        }
    }

    private suspend fun handleToggleWatchlist(
        flowCollector: FlowCollector<DetailResults>,
        toggle: suspend () -> DataState<MarkResponse>
    ) {
        flowCollector.emit(DetailResults.ToggleWatchlistResult(CommonViewState(isLoading = true), 0))
        when (val result = toggle()) {
            is DataState.Success -> {
                val code = result.data.status_code
                when (code) {
                    1 ->{
                        flowCollector.emit(DetailResults.ToggleWatchlistResult(CommonViewState(data = true), code))
                    }
                    13 -> flowCollector.emit(DetailResults.ToggleWatchlistResult(CommonViewState(data = false), code))
                    else -> flowCollector.emit(DetailResults.ToggleCodeResult( code ))
                }
            }
            is DataState.Error -> flowCollector.emit(DetailResults.ToggleCodeResult( -1 ))
            else -> {}
        }
    }


    private suspend fun handleGetSessionId(flowCollector: FlowCollector<DetailResults>) {
        val sessionId=sessionManager.getSessionId().firstOrNull()
        flowCollector.emit(DetailResults.SessionIdLoaded(sessionId))

    }

    private suspend fun handleGetPeopleCredits(
        flowCollector: FlowCollector<DetailResults>,
        getPeopleCredits: suspend () -> DataState<List<MediaItem>>
    ) {
        flowCollector.emit(DetailResults.Loading(true))



        when(val result=getPeopleCredits()){

            is DataState.Success -> flowCollector.emit(
                DetailResults.PeopleCreditsLoad(CommonViewState(data = result.data, isSuccess = true))
            )
            is DataState.Error -> flowCollector.emit(
                DetailResults.PeopleCreditsLoad(CommonViewState(errorThrowable = result.throwable))
            )
            is DataState.Empty -> flowCollector.emit(
                DetailResults.PeopleCreditsLoad(CommonViewState(isEmpty = true))
            )
            else -> {}


        }



        flowCollector.emit(DetailResults.Loading(false))

    }

    private suspend fun handleGetReviews(
        flowCollector: FlowCollector<DetailResults>,
        getReviews:  suspend () -> DataState<List<Review>>
    ) {
        flowCollector.emit(DetailResults.Loading(true))

        when(val result=getReviews()){

            is DataState.Success -> flowCollector.emit(
                DetailResults.ReviewsLoad(CommonViewState(data = result.data, isSuccess = true))
            )
            is DataState.Error -> flowCollector.emit(
                DetailResults.ReviewsLoad(CommonViewState(errorThrowable = result.throwable))
            )
            is DataState.Empty -> flowCollector.emit(
                DetailResults.ReviewsLoad(CommonViewState(isEmpty = true))
            )
            else -> {}


        }


        flowCollector.emit(DetailResults.Loading(false))


    }

    private suspend fun handleGetSimilar(
        flowCollector: FlowCollector<DetailResults>,
        getSimilar: suspend () -> DataState<List<MediaItem>>
    ) {
        flowCollector.emit(DetailResults.Loading(true))


        when(val result=getSimilar()){

            is DataState.Success -> flowCollector.emit(
                DetailResults.SimilarLoad(CommonViewState(data = result.data, isSuccess = true))
            )
            is DataState.Error -> flowCollector.emit(
                DetailResults.SimilarLoad(CommonViewState(errorThrowable = result.throwable))
            )
            is DataState.Empty -> flowCollector.emit(
                DetailResults.SimilarLoad(CommonViewState(isEmpty = true))
            )
            else -> {}


        }


        flowCollector.emit(DetailResults.Loading(false))

    }


    private suspend fun handleGetCredits(
        flowCollector: FlowCollector<DetailResults>,
        getCredits: suspend () -> DataState<CreditsResponse>
    ) {
        flowCollector.emit(DetailResults.Loading(true))

        when(val result=getCredits()){

            is DataState.Success -> flowCollector.emit(
                DetailResults.CreditsLoad(CommonViewState(data = result.data, isSuccess = true))
            )
            is DataState.Error -> flowCollector.emit(
                DetailResults.CreditsLoad(CommonViewState(errorThrowable = result.throwable))
            )
            is DataState.Empty -> flowCollector.emit(
                DetailResults.CreditsLoad(CommonViewState(isEmpty = true))
            )
            else -> {}


        }


        flowCollector.emit(DetailResults.Loading(false))


    }

    private suspend fun handleGetVideo(
        flowCollector: FlowCollector<DetailResults>,
         getVideo: suspend () -> DataState<List<VideoItem>>
    ) {


        flowCollector.emit(DetailResults.Loading(true))

        when(val result=getVideo()){

            is DataState.Success -> flowCollector.emit(
                DetailResults.VideoList(CommonViewState(data = result.data, isSuccess = true))
            )
            is DataState.Error -> flowCollector.emit(
                DetailResults.VideoList(CommonViewState(errorThrowable = result.throwable))
            )
            is DataState.Empty -> flowCollector.emit(
                DetailResults.VideoList(CommonViewState(isEmpty = true))
            )
            else -> {}


        }
        flowCollector.emit(DetailResults.Loading(false))


    }

    private suspend fun handleGetDetailMovie(
        flowCollector: FlowCollector<DetailResults>,
        getMediaItem: suspend () -> DataState<DetailMediaItem>
    ) {
        flowCollector.emit(DetailResults.Loading(true))
        when(val result=getMediaItem()){
            is DataState.Success -> flowCollector.emit(
                DetailResults.DetailMediaLoaded(CommonViewState(data = result.data()))
            )
            is DataState.Error -> flowCollector.emit(
                DetailResults.DetailMediaLoaded(CommonViewState(errorThrowable = result.throwable))
            )
            is DataState.Empty -> flowCollector.emit(
                DetailResults.DetailMediaLoaded(CommonViewState(isEmpty = true))
            )
            else -> {}



        }


        flowCollector.emit(DetailResults.Loading(false))



    }

}
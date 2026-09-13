package com.besha.shinobihub.features.detail.presentaion.viewmodel

import app.cash.turbine.test
import com.besha.shinobihub.appcore.data.local.SessionManager
import com.besha.shinobihub.appcore.domain.DataState
import com.besha.shinobihub.appcore.domain.model.MediaItem
import com.besha.shinobihub.appcore.domain.model.MediaType
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse
import com.besha.shinobihub.features.detail.data.model.mark.MarkRequest
import com.besha.shinobihub.features.detail.data.model.mark.MarkResponse
import com.besha.shinobihub.features.detail.data.model.review.Review
import com.besha.shinobihub.features.detail.data.model.status.AccountStatesResponse
import com.besha.shinobihub.features.detail.data.model.video.VideoItem
import com.besha.shinobihub.features.detail.domain.constants.DetailTab
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
import com.besha.shinobihub.testutil.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getDetailMovieUseCase: GetDetailMovieUseCase = mockk()
    private val getDetailTvUseCase: GetDetailTvUseCase = mockk()
    private val getDetailPersonUseCase: GetDetailPersonUseCase = mockk()
    private val getMovieVideoUseCase: GetMovieVideoUseCase = mockk()
    private val getTvVideoUseCase: GetTvVideoUseCase = mockk()
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase = mockk()
    private val getTvCreditsUseCase: GetTvCreditsUseCase = mockk()
    private val getPeopleCreditsUseCase: GetPeopleCreditsUseCase = mockk()
    private val getMoviesSimilarUseCase: GetMoviesSimilarUseCase = mockk()
    private val getTvSimilarUseCase: GetTvSimilarUseCase = mockk()
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase = mockk()
    private val getTvReviewsUseCase: GetTvReviewsUseCase = mockk()
    private val getMovieAccountStateUseCase: GetMovieAccountStateUseCase = mockk()
    private val getTvAccountStateUseCase: GetTvAccountStateUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase = mockk()
    private val sessionManager: SessionManager = mockk()

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        viewModel =
            DetailViewModel(
                getDetailMovieUseCase, getDetailTvUseCase, getDetailPersonUseCase,
                getMovieVideoUseCase, getTvVideoUseCase, getMovieCreditsUseCase,
                getTvCreditsUseCase, getPeopleCreditsUseCase, getMoviesSimilarUseCase,
                getTvSimilarUseCase, getMovieReviewsUseCase, getTvReviewsUseCase,
                getMovieAccountStateUseCase, getTvAccountStateUseCase,
                toggleFavoriteUseCase, toggleWatchlistUseCase, sessionManager,
            )
    }

    @Test
    fun `SwitchTab emits SwitchTab result`() =
        runTest {
            val tab = DetailTab.REVIEWS
            viewModel.handleAction(DetailActions.SwitchTab(tab)).test {
                val result = awaitItem() as DetailResults.SwitchTab
                assertThat(result.state.data).isEqualTo(tab)
                awaitComplete()
            }
        }

    @Test
    fun `GetDetailMovie emits loading then success`() =
        runTest {
            val movieId = 123
            val detailItem = mockk<DetailMediaItem>()
            coEvery { getDetailMovieUseCase(movieId) } returns DataState.Success(detailItem)

            viewModel.handleAction(DetailActions.GetDetailMovie(movieId)).test {
                assertThat((awaitItem() as DetailResults.Loading).state).isTrue()
                val result = awaitItem() as DetailResults.DetailMediaLoaded
                assertThat(result.state.data).isEqualTo(detailItem)
                assertThat((awaitItem() as DetailResults.Loading).state).isFalse()
                awaitComplete()
            }
        }

    @Test
    fun `GetMovieVideo emits loading then success`() =
        runTest {
            val movieId = 123
            val videos = listOf(VideoItem(id = "1", key = "key"))
            coEvery { getMovieVideoUseCase(movieId) } returns DataState.Success(videos)

            viewModel.handleAction(DetailActions.GetMovieVideo(movieId)).test {
                assertThat((awaitItem() as DetailResults.Loading).state).isTrue()
                val result = awaitItem() as DetailResults.VideoList
                assertThat(result.state.data).isEqualTo(videos)
                assertThat((awaitItem() as DetailResults.Loading).state).isFalse()
                awaitComplete()
            }
        }

    @Test
    fun `GetMovieCredits emits loading then success`() =
        runTest {
            val movieId = 123
            val credits = CreditsResponse(id = 123, cast = emptyList(), crew = emptyList())
            coEvery { getMovieCreditsUseCase(movieId) } returns DataState.Success(credits)

            viewModel.handleAction(DetailActions.GetMovieCredits(movieId)).test {
                assertThat((awaitItem() as DetailResults.Loading).state).isTrue()
                val result = awaitItem() as DetailResults.CreditsLoad
                assertThat(result.state.data).isEqualTo(credits)
                assertThat((awaitItem() as DetailResults.Loading).state).isFalse()
                awaitComplete()
            }
        }

    @Test
    fun `GetMovieSimilar emits loading then success`() =
        runTest {
            val movieId = 123
            val similar = listOf(MediaItem(id = 1, media_type = MediaType.Movies))
            coEvery { getMoviesSimilarUseCase(movieId) } returns DataState.Success(similar)

            viewModel.handleAction(DetailActions.GetMovieSimilar(movieId)).test {
                assertThat((awaitItem() as DetailResults.Loading).state).isTrue()
                val result = awaitItem() as DetailResults.SimilarLoad
                assertThat(result.state.data).isEqualTo(similar)
                assertThat((awaitItem() as DetailResults.Loading).state).isFalse()
                awaitComplete()
            }
        }

    @Test
    fun `GetMovieReviews emits loading then success`() =
        runTest {
            val movieId = 123
            val reviews = listOf(Review(id = "1", author = "me"))
            coEvery { getMovieReviewsUseCase(movieId) } returns DataState.Success(reviews)

            viewModel.handleAction(DetailActions.GetMovieReviews(movieId)).test {
                assertThat((awaitItem() as DetailResults.Loading).state).isTrue()
                val result = awaitItem() as DetailResults.ReviewsLoad
                assertThat(result.state.data).isEqualTo(reviews)
                assertThat((awaitItem() as DetailResults.Loading).state).isFalse()
                awaitComplete()
            }
        }

    @Test
    fun `GetMovieAccountState emits success when session exists`() =
        runTest {
            val movieId = 123
            val sessionId = "session_xyz"
            val accountState = AccountStatesResponse(favorite = true, watchlist = false, id = movieId)

            every { sessionManager.getSessionId() } returns flowOf(sessionId)
            coEvery { getMovieAccountStateUseCase(movieId, sessionId) } returns DataState.Success(accountState)

            viewModel.handleAction(DetailActions.GetMovieAccountState(movieId)).test {
                assertThat((awaitItem() as DetailResults.Loading).state).isTrue()
                val result = awaitItem() as DetailResults.AccountStateLoaded
                assertThat(result.favorite).isTrue()
                assertThat(result.watchlist).isFalse()
                assertThat((awaitItem() as DetailResults.Loading).state).isFalse()
                awaitComplete()
            }
        }

    @Test
    fun `ToggleFavorite emits ToggleFavoriteResult with code 1 for added`() =
        runTest {
            val markRequest = MarkRequest(media_id = 1, media_type = "movie", favorite = true)
            val sessionId = "session_xyz"
            val accountId = 1
            val markResponse = MarkResponse(status_code = 1, status_message = "Added")

            every { sessionManager.getSessionId() } returns flowOf(sessionId)
            every { sessionManager.getAccountId() } returns flowOf(accountId)
            coEvery { toggleFavoriteUseCase(accountId, markRequest, sessionId) } returns DataState.Success(markResponse)

            viewModel.handleAction(DetailActions.ToggleFavorite(markRequest)).test {
                assertThat((awaitItem() as DetailResults.ToggleFavoriteResult).isFavorite.isLoading).isTrue()
                val result = awaitItem() as DetailResults.ToggleFavoriteResult
                assertThat(result.isFavorite.data).isTrue()
                assertThat(result.code).isEqualTo(1)
                awaitComplete()
            }
        }

    @Test
    fun `ToggleWatchList emits ToggleWatchlistResult with code 1 for added`() =
        runTest {
            val markRequest = MarkRequest(media_id = 1, media_type = "movie", watchlist = true)
            val sessionId = "session_xyz"
            val accountId = 1
            val markResponse = MarkResponse(status_code = 1, status_message = "Added")

            every { sessionManager.getSessionId() } returns flowOf(sessionId)
            every { sessionManager.getAccountId() } returns flowOf(accountId)
            coEvery { toggleWatchlistUseCase(accountId, markRequest, sessionId) } returns DataState.Success(markResponse)

            viewModel.handleAction(DetailActions.ToggleWatchList(markRequest)).test {
                assertThat((awaitItem() as DetailResults.ToggleWatchlistResult).isWatchlist.isLoading).isTrue()
                val result = awaitItem() as DetailResults.ToggleWatchlistResult
                assertThat(result.isWatchlist.data).isTrue()
                assertThat(result.code).isEqualTo(1)
                awaitComplete()
            }
        }

    @Test
    fun `GetSessionId emits SessionIdLoaded`() =
        runTest {
            val sessionId = "test_session"
            every { sessionManager.getSessionId() } returns flowOf(sessionId)

            viewModel.handleAction(DetailActions.GetSessionId).test {
                val result = awaitItem() as DetailResults.SessionIdLoaded
                assertThat(result.state).isEqualTo(sessionId)
                awaitComplete()
            }
        }

    @Test
    fun `ResetToggleCode emits ToggleCodeResult with 0`() =
        runTest {
            viewModel.handleAction(DetailActions.ResetToggleCode).test {
                val result = awaitItem() as DetailResults.ToggleCodeResult
                assertThat(result.code).isEqualTo(0)
                awaitComplete()
            }
        }
}

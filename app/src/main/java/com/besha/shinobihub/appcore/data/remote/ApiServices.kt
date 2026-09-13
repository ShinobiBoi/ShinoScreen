package com.besha.shinobihub.appcore.data.remote

import com.besha.shinobihub.BuildConfig
import com.besha.shinobihub.appcore.data.model.genre.GenreResponse
import com.besha.shinobihub.appcore.data.model.movie.MediaResponse
import com.besha.shinobihub.features.detail.data.model.credits.CreditsResponse
import com.besha.shinobihub.features.detail.data.model.detailitem.DetailMediaItemDto
import com.besha.shinobihub.features.detail.data.model.mark.MarkRequest
import com.besha.shinobihub.features.detail.data.model.mark.MarkResponse
import com.besha.shinobihub.features.detail.data.model.review.ReviewResponse
import com.besha.shinobihub.features.detail.data.model.status.AccountStatesResponse
import com.besha.shinobihub.features.detail.data.model.video.VideoResponse
import com.besha.shinobihub.features.home.data.model.account.AccountResponse
import com.besha.shinobihub.features.login.data.model.login.LoginRequest
import com.besha.shinobihub.features.login.data.model.login.LoginResponse
import com.besha.shinobihub.features.login.data.model.session.SessionRequest
import com.besha.shinobihub.features.login.data.model.session.SessionResponse
import com.besha.shinobihub.features.login.data.model.token.TokenResponse
import com.besha.shinobihub.features.profile.data.model.DeleteSessionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    // AUTH////////////////////////////////////////////////

    @GET("authentication/token/new")
    suspend fun createRequestToken(
        @Query("api_key") key: String = API_KEY,
    ): Response<TokenResponse>

    @POST("authentication/token/validate_with_login")
    suspend fun validateWithLogin(
        @Body body: LoginRequest,
        @Query("api_key") key: String = API_KEY,
    ): Response<LoginResponse>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Body body: SessionRequest,
        @Query("api_key") key: String = API_KEY,
    ): Response<SessionResponse>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Body body: DeleteSessionRequest,
        @Query("api_key") key: String = API_KEY,
    ): Response<SessionResponse>

    @GET("account")
    suspend fun getAccount(
        @Query("session_id") sessionId: String,
        @Query("api_key") key: String = API_KEY,
    ): Response<AccountResponse>

    // ACCOUNT ACTIONS//////////////////////////////////////////////

    @POST("account/{account_id}/watchlist")
    suspend fun toggleWatchlist(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Body body: MarkRequest,
        @Query("api_key") key: String = API_KEY,
    ): Response<MarkResponse>

    @POST("account/{account_id}/favorite")
    suspend fun toggleFavorite(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Body body: MarkRequest,
        @Query("api_key") key: String = API_KEY,
    ): Response<MarkResponse>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavoriteTv(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchlistMovies(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("account/{account_id}/watchlist/tv")
    suspend fun getWatchlistTv(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieAccountStates(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<AccountStatesResponse>

    @GET("tv/{tv_id}/account_states")
    suspend fun getTvAccountStates(
        @Path("tv_id") tvId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<AccountStatesResponse>

    // TRENDING////////////////////////////////////////////

    @GET("trending/all/week")
    suspend fun getTrendingAll(
        @Query("page") page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("trending/tv/week")
    suspend fun getTrendingTv(
        @Query("page") page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("trending/person/week")
    suspend fun getTrendingPeople(
        @Query("page") page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    // MOVIES//////////////////////////////////////////////////////

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page")page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page")page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    // TV SERIES///////////////////////////////////////////////

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTv(
        @Query("page")page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("page")page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(
        @Query("page")page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    // Detail////////////////////////////////////////

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<DetailMediaItemDto>

    @GET("tv/{series_id}")
    suspend fun getDetailTv(
        @Path("series_id") seriesId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<DetailMediaItemDto>

    @GET("person/{person_id}")
    suspend fun getDetailPerson(
        @Path("person_id") personId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<DetailMediaItemDto>

    // SEARCH////////////////////////////////////////

    @GET("search/multi")
    suspend fun searchMulti(
        @Query("query")query: String,
        @Query("page")page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query")query: String,
        @Query("page")page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("search/tv")
    suspend fun searchTv(
        @Query("query")query: String,
        @Query("page")page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("search/person")
    suspend fun searchPeople(
        @Query("query")query: String,
        @Query("page")page: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<GenreResponse>

    // VIDEOS//////////////////////////////////////

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<VideoResponse>

    @GET("tv/{series_id}/videos")
    suspend fun getTvVideo(
        @Path("series_id") seriesId: Int,
        @Query("api_key")key: String = API_KEY,
    ): Response<VideoResponse>

    // CREDITS///////////////////////////////////////

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<CreditsResponse>

    @GET("tv/{series_id}/credits")
    suspend fun getTvCredits(
        @Path("series_id") seriesId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<CreditsResponse>

    @GET("person/{person_id}/combined_credits")
    suspend fun getPersonCredits(
        @Path("person_id") personId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    // SIMILAR//////////////////////////

    @GET("movie/{movie_id}/similar")
    suspend fun getMovieSimilar(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    @GET("tv/{series_id}/similar")
    suspend fun getTvSimilar(
        @Path("series_id") seriesId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<MediaResponse>

    // REVIEWS/////////////////////////

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<ReviewResponse>

    @GET("tv/{series_id}/reviews")
    suspend fun getTvReviews(
        @Path("series_id") seriesId: Int,
        @Query("api_key") key: String = API_KEY,
    ): Response<ReviewResponse>

    // Discover///////////////////////////////////////////////
    @GET("discover/movie")
    suspend fun getMovieDiscover(
        @Query("with_genres") genresId: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<MediaResponse>

    @GET("discover/tv")
    suspend fun getTvDiscover(
        @Query("with_genres") genresId: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<MediaResponse>

    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }
}

// api -> endpoint

// retrofit -(base url

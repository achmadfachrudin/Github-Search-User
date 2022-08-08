package com.achmad.baseandroid.service

import com.achmad.feature.github.data.entity.SearchUserEntity
import com.achmad.feature.github.data.entity.UserEntity
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    suspend fun searchUserList(
        @Query("q") query: String,
        @Query("page") page: Int
    ): ApiResponse<SearchUserEntity>

    @GET("users/{username}")
    suspend fun fetchUser(
        @Path("username") username: String
    ): ApiResponse<UserEntity>
}

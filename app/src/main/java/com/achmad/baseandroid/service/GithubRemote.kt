package com.achmad.baseandroid.service

import com.achmad.feature.github.data.entity.SearchUserEntity
import com.achmad.feature.github.data.entity.UserEntity
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class GithubRemote @Inject constructor(
    private val service: GithubService
) {

    suspend fun searchUserList(
        query: String,
        page: Int
    ): ApiResponse<SearchUserEntity> = service.searchUserList(
        query = query,
        page = page
    )

    suspend fun fetchUser(username: String): ApiResponse<UserEntity> = service.fetchUser(username)
}

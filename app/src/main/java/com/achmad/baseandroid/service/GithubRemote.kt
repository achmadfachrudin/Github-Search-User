package com.achmad.baseandroid.service

import com.achmad.feature.github.data.entity.SearchUserEntity
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
}

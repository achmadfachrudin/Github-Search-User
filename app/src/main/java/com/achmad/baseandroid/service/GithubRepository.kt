package com.achmad.baseandroid.service

import androidx.annotation.WorkerThread
import com.achmad.common.ApiResult
import com.achmad.feature.github.data.mapper.toUser
import com.achmad.feature.github.data.mapper.toUserList
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val remote: GithubRemote
) {

    @WorkerThread
    fun searchUserList(
        query: String,
        page: Int
    ) = flow {
        emit(ApiResult.Loading)

        val response = remote.searchUserList(
            query = query,
            page = page
        )

        response.suspendOnSuccess {
            emit(ApiResult.Success(data.toUserList()))
        }.suspendOnError {
            // handles error cases
            emit(ApiResult.Error(this.toString()))
        }.suspendOnException {
            // handles exceptional cases
            emit(ApiResult.Error(this.toString()))
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchUser(
        username: String
    ) = flow {
        emit(ApiResult.Loading)

        val response = remote.fetchUser(username)

        response.suspendOnSuccess {
            emit(ApiResult.Success(data.toUser()))
        }.suspendOnError {
            // handles error cases
            emit(ApiResult.Error(this.toString()))
        }.suspendOnException {
            // handles exceptional cases
            emit(ApiResult.Error(this.toString()))
        }
    }.flowOn(Dispatchers.IO)
}

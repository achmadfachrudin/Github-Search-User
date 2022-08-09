package com.achmad.baseandroid.service

import com.achmad.common.ApiResult
import com.achmad.feature.github.data.entity.SearchUserEntity
import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

internal class SearchUserListTest : GithubRepositoryTest() {

    @Test
    fun `searchUserList success should return user list`() {
        val query = "achmad"
        val page = 1
        val result = SearchUserEntity(
            totalCount = 1,
            items = listOf(userEntity.copy(login = "achmad"))
        )

        coEvery {
            remote.searchUserList(query, page)
        } returns ApiResponse.Success(Response.success(result))

        val actual = runBlocking { repository.searchUserList(query, page).last() }

        Assert.assertEquals(ApiResult.Success(result).status, actual.status)
        Assert.assertEquals(result.items?.size, actual.data?.size)
    }

    @Test
    fun `searchUserList error should return error`() {
        val query = "achmad"
        val page = 1
        val result = Response.error<SearchUserEntity>(
            400,
            "".toResponseBody()
        )

        coEvery {
            remote.searchUserList(query, page)
        } returns ApiResponse.Failure.Error(result)

        val actual = runBlocking { repository.searchUserList(query, page).last() }

        Assert.assertEquals(ApiResult.Error("").status, actual.status)
    }
}

package com.achmad.baseandroid.service

import com.achmad.common.ApiResult
import com.achmad.feature.github.data.entity.RepositoryEntity
import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

internal class FetchRepositoryTest : GithubRepositoryTest() {

    @Test
    fun `fetchRepository success should return user`() {
        val username = "achmad"
        val result = listOf(
            repositoryEntity.copy(name = "repo 1"),
            repositoryEntity.copy(name = "repo 2"),
            repositoryEntity.copy(name = "repo 3")
        )

        coEvery {
            remote.fetchRepository(username)
        } returns ApiResponse.Success(Response.success(result))

        val actual = runBlocking { repository.fetchRepository(username).last() }

        Assert.assertEquals(ApiResult.Success(result).status, actual.status)
        Assert.assertEquals(result.size, actual.data?.size)
    }

    @Test
    fun `fetchRepository error should return error`() {
        val username = "achmad"
        val result = Response.error<List<RepositoryEntity>>(
            400,
            "".toResponseBody()
        )

        coEvery {
            remote.fetchRepository(username)
        } returns ApiResponse.Failure.Error(result)

        val actual = runBlocking { repository.fetchRepository(username).last() }

        Assert.assertEquals(ApiResult.Error("").status, actual.status)
    }
}

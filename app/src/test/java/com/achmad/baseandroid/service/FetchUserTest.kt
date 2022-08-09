package com.achmad.baseandroid.service

import com.achmad.common.ApiResult
import com.achmad.feature.github.data.entity.UserEntity
import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

internal class FetchUserTest : GithubRepositoryTest() {

    @Test
    fun `fetchUser success should return user`() {
        val username = "achmad"
        val result = userEntity.copy(login = username)

        coEvery {
            remote.fetchUser(username)
        } returns ApiResponse.Success(Response.success(result))

        val actual = runBlocking { repository.fetchUser(username).last() }

        Assert.assertEquals(ApiResult.Success(result).status, actual.status)
        Assert.assertEquals(username, actual.data?.username)
    }

    @Test
    fun `fetchUser error should return error`() {
        val username = "achmad"
        val result = Response.error<UserEntity>(
            400,
            "".toResponseBody()
        )

        coEvery {
            remote.fetchUser(username)
        } returns ApiResponse.Failure.Error(result)

        val actual = runBlocking { repository.fetchUser(username).last() }

        Assert.assertEquals(ApiResult.Error("").status, actual.status)
    }
}

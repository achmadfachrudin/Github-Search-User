package com.achmad.feature.github.data.mapper

import com.achmad.feature.github.data.entity.RepositoryEntity
import com.achmad.feature.github.data.entity.SearchUserEntity
import com.achmad.feature.github.data.entity.UserEntity
import com.achmad.feature.github.data.model.Repository
import com.achmad.feature.github.data.model.User
import org.junit.Assert
import org.junit.Test

internal class GithubMapperKtTest {

    @Test
    fun `map search user entity to user list`() {
        val entity = SearchUserEntity(
            totalCount = 0,
            items = listOf(
                UserEntity(
                    login = "",
                    name = "",
                    bio = "",
                    email = "",
                    location = "",
                    followers = 0,
                    following = 0,
                    url = "",
                    avatarUrl = "",
                    repoUrl = ""
                )
            )
        )

        val actual = entity.toUserList()

        val expected = listOf(
            User(
                username = "",
                name = "",
                bio = "",
                email = "",
                location = "",
                followers = 0,
                following = 0,
                userUrl = "",
                avatarUrl = "",
                repoUrl = ""
            )
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `map user entity to user`() {
        val entity = UserEntity(
            login = "",
            name = "",
            bio = "",
            email = "",
            location = "",
            followers = 0,
            following = 0,
            url = "",
            avatarUrl = "",
            repoUrl = ""
        )

        val actual = entity.toUser()

        val expected = User(
            username = "",
            name = "",
            bio = "",
            email = "",
            location = "",
            followers = 0,
            following = 0,
            userUrl = "",
            avatarUrl = "",
            repoUrl = ""
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `map repository entity to repository`() {
        val entity = RepositoryEntity(
            name = "",
            description = "",
            stargazersCount = 0,
            updatedAt = ""
        )

        val actual = entity.toRepository()

        val expected = Repository(
            name = "",
            description = "",
            stargazersCount = 0,
            updatedAt = ""
        )

        Assert.assertEquals(expected, actual)
    }
}

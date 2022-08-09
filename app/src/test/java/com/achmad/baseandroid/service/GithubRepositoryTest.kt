package com.achmad.baseandroid.service

import com.achmad.feature.github.data.entity.RepositoryEntity
import com.achmad.feature.github.data.entity.UserEntity
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Before

open class GithubRepositoryTest {

    protected val remote: GithubRemote = mockk()

    protected val repository = GithubRepository(
        remote
    )

    protected val userEntity = UserEntity(
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

    protected val repositoryEntity = RepositoryEntity(
        name = "",
        description = "",
        stargazersCount = 0,
        updatedAt = ""
    )

    @Before
    fun setup() {
        clearAllMocks()
    }
}

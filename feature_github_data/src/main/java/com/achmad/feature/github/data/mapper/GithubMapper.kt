package com.achmad.feature.github.data.mapper

import com.achmad.feature.github.data.entity.RepositoryEntity
import com.achmad.feature.github.data.entity.SearchUserEntity
import com.achmad.feature.github.data.entity.UserEntity
import com.achmad.feature.github.data.model.Repository
import com.achmad.feature.github.data.model.User

fun SearchUserEntity.toUserList(): List<User> {
    return items?.map { user ->
        user.toUser()
    } ?: emptyList()
}

fun UserEntity.toUser(): User {
    return User(
        username = login.orEmpty(),
        name = name.orEmpty(),
        bio = bio.orEmpty(),
        email = email.orEmpty(),
        location = location.orEmpty(),
        followers = followers ?: 0,
        following = following ?: 0,
        userUrl = url.orEmpty(),
        avatarUrl = avatarUrl.orEmpty(),
        repoUrl = repoUrl.orEmpty()
    )
}

fun List<RepositoryEntity>.toRepositoryList(): List<Repository> {
    return map { repository ->
        repository.toRepository()
    }
}

fun RepositoryEntity.toRepository(): Repository {
    return Repository(
        name = name.orEmpty(),
        description = description.orEmpty(),
        stargazersCount = stargazersCount ?: 0,
        updatedAt = updatedAt.orEmpty()
    )
}

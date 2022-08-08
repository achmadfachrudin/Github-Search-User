package com.achmad.feature.github.data.mapper

import com.achmad.feature.github.data.entity.SearchUserEntity
import com.achmad.feature.github.data.entity.UserEntity
import com.achmad.feature.github.data.model.User

fun SearchUserEntity.toUserList(): List<User> {
    return items?.map { user ->
        User(
            username = user.login.orEmpty(),
            name = "",
            bio = "",
            email = "",
            location = "",
            followers = 0,
            following = 0,
            userUrl = user.url.orEmpty(),
            avatarUrl = user.avatarUrl.orEmpty(),
            repoUrl = user.repoUrl.orEmpty()
        )
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

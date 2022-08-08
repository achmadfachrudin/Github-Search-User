package com.achmad.feature.github.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserEntity(
    @field:Json(name = "login") val login: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "bio") val bio: String?,
    @field:Json(name = "email") val email: String?,
    @field:Json(name = "location") val location: String?,
    @field:Json(name = "followers") val followers: Int?,
    @field:Json(name = "following") val following: Int?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "avatar_url") val avatarUrl: String?,
    @field:Json(name = "repos_url") val repoUrl: String?
)

package com.achmad.feature.github.data.model

data class User(
    val username: String,
    val name: String,
    val bio: String,
    val email: String,
    val location: String,
    val followers: Int,
    val following: Int,
    val userUrl: String,
    val avatarUrl: String,
    val repoUrl: String
)

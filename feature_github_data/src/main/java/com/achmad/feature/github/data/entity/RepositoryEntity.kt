package com.achmad.feature.github.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryEntity(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "stargazers_count") val stargazersCount: Int?,
    @field:Json(name = "updated_at") val updatedAt: String?
)

package com.achmad.feature.github.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchUserEntity(
    @field:Json(name = "total_count") val totalCount: Int?,
    @field:Json(name = "items") val items: List<UserEntity>?
)

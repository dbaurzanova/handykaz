package com.example.handykaz.resources

data class VideoClass(
    val id: Int,
    val title: String,
    val thumbnailResourceId: Int,
    val isWatched: Boolean,
    val videoUrl: String
)

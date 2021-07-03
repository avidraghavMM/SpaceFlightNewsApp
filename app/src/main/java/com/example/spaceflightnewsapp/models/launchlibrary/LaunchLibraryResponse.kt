package com.example.spaceflightnewsapp.models.launchlibrary

data class LaunchLibraryResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>?
)
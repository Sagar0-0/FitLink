package com.example.fitlink.model

data class FitLinkPost(
    val user: User,
    var title: String = "",
    var category: String = "",
    var imageUrl: String = "",
    var workoutPlan: WorkoutPlan
)

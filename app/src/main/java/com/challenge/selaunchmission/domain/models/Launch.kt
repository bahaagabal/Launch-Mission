package com.challenge.selaunchmission.domain.models

data class Launch(
    val id: String,
    val site: String,
    val mission: Mission,
    val rocket: Rocket,
    val isBooked: Boolean,
)

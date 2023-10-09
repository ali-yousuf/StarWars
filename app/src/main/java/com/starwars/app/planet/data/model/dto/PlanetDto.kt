package com.starwars.app.planet.data.model.dto


import com.google.gson.annotations.SerializedName

data class PlanetDto(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Result>
)
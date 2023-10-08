package com.starwars.app.starship.presentation

import com.starwars.app.starship.domain.entity.Starship

data class StarshipState (
    val starships : List<Starship>
)
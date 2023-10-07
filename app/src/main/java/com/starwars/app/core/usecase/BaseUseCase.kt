package com.starwars.app.core.usecase

interface BaseUseCase<In, Out> {
    suspend fun execute(input: In): Out
}
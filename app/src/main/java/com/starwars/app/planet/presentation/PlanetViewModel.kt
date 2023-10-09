package com.starwars.app.planet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.starwars.app.planet.domain.entity.Planet
import com.starwars.app.planet.domain.usecase.GetPlanetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val getPlanetUseCase: GetPlanetsUseCase
) : ViewModel(){
    private val _planetsState: MutableStateFlow<PagingData<Planet>> = MutableStateFlow(value = PagingData.empty())
    val planetsState: MutableStateFlow<PagingData<Planet>> get() = _planetsState

    init {
        getPlanets()
    }

    private fun  getPlanets(){
        viewModelScope.launch {
            getPlanetUseCase.execute(Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _planetsState.value = it
                }
        }
    }
}
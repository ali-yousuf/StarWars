package com.starwars.app.starship.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.starwars.app.starship.domain.entity.Starship
import com.starwars.app.starship.domain.usecase.GetStarshipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StarshipViewModel @Inject constructor (
    private val getStarshipUseCase: GetStarshipUseCase
) : ViewModel(){
    private val _starshipsState: MutableStateFlow<PagingData<Starship>> = MutableStateFlow(value = PagingData.empty())
    val starshipsState: MutableStateFlow<PagingData<Starship>> get() = _starshipsState

    init {
        getStarships()
    }

    private fun  getStarships(){
        viewModelScope.launch {
            getStarshipUseCase.execute(Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _starshipsState.value = it
                }
        }
    }
}
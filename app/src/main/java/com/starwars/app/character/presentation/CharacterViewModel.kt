package com.starwars.app.character.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.starwars.app.character.domain.entity.Character
import com.starwars.app.character.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CharacterViewModel @Inject constructor (
    private val getCharactersUseCase: GetCharactersUseCase
): ViewModel() {
    private val _characterState: MutableStateFlow<PagingData<Character>> = MutableStateFlow(value = PagingData.empty())
    val charactersState: MutableStateFlow<PagingData<Character>> get() = _characterState

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            getCharactersUseCase.execute(Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _characterState.value = it
                }
        }
    }

}




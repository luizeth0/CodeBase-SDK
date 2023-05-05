package com.challenge.code_base_sdk.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.code_base_sdk.model.domain.DomainCharacter
import com.challenge.code_base_sdk.usecase.CharacterUseCase
import com.challenge.code_base_sdk.utils.AppType
import com.challenge.code_base_sdk.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel responsible for handling character-related data and state for the MainBaseActivity.
 * @param charactersUseCase that provides access to characterUseCase
 */
@HiltViewModel
class MainBaseViewModel @Inject constructor(
    private val charactersUseCase: CharacterUseCase
) : ViewModel() {

    //var appType: AppType? = null

    // Mutable state flow to hold the selected character item
    private val _selectedItem: MutableStateFlow<DomainCharacter?> = MutableStateFlow(null)
    val selectedItem: StateFlow<DomainCharacter?> get() = _selectedItem
    //var selectedItem: DomainCharacter? = null

    // Mutable state flow to hold the list of characters along with the current loading/error state
    private val _characters: MutableStateFlow<ResultState<List<DomainCharacter>>> = MutableStateFlow(ResultState.LOADING)
    val characters: StateFlow<ResultState<List<DomainCharacter>>> get() = _characters

    // Mutable live data to hold the search query entered by the user
    private val _nexText: MutableLiveData<String> = MutableLiveData("")
    val nextText: LiveData<String> get() = _nexText

    /**
     * Updates the selected character item in the view model.
     * @param item The selected character item.
     */
    fun setSelectedItem(item: DomainCharacter){
        _selectedItem.value = item
    }

    /**
     * Fetches the list of characters from the use case class.
     * @param appType The type of app to fetch characters for.
     */
    fun getCharacters(appType: String?) {
        appType?.let {
            viewModelScope.launch {
                charactersUseCase.invoke(it).collect {
                    _characters.value = it
                }
            }
        }
    }

    /**
     * Updates the search query in the view model.
     * @param newText The new search query entered by the user.
     */
    fun searchItems(newText: String?) {
        newText?.let {
            _nexText.value = it
        }
    }

}
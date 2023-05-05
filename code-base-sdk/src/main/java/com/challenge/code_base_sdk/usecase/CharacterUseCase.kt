package com.challenge.code_base_sdk.usecase

import com.challenge.code_base_sdk.model.domain.DomainCharacter
import com.challenge.code_base_sdk.rest.CharacterRepository
import com.challenge.code_base_sdk.utils.AppType
import com.challenge.code_base_sdk.utils.ResultState
import com.challenge.code_base_sdk.views.adapter.AppAdapter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
* The CharacterUseCase class provides a use case for getting a list of domain characters based on the app type.
* @property repository a repository that provides access to character data
*/
class CharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {

    /**
     * Invokes the use case to get a list of domain characters based on the app type.
     * @param appType the app type to get the characters for
     * @return a flow of ResultState that represents the result of the operation
     */
    operator fun invoke(appType: String): Flow<ResultState<List<DomainCharacter>>> =
        repository.getCharacters(appType)

}
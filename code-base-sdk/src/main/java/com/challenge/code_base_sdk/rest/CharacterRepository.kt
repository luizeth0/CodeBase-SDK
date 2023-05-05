package com.challenge.code_base_sdk.rest

import com.challenge.code_base_sdk.model.domain.DomainCharacter
import com.challenge.code_base_sdk.model.domain.mapToDomainCharacters
import com.challenge.code_base_sdk.utils.AppType
import com.challenge.code_base_sdk.utils.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * This interface declares a function called getCharacters
 * which returns a Flow of ResultState containing a list of DomainCharacter objects.
 */
interface CharacterRepository {
    /**
     * Fetches characters of the given [type] and returns a flow of [ResultState] [List] [DomainCharacter].
     */
    fun getCharacters(type: String): Flow<ResultState<List<DomainCharacter>>>
}

/**
 * Implementation of [CharacterRepository] interface.
 * Uses [ServiceApi] to make network calls and returns [ResultState] wrapped [DomainCharacter]s in a flow.
 */
class CharacterRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi,
    private val ioDispatcher: CoroutineDispatcher
): CharacterRepository {

    /**
     * Fetches characters of the given [type] and returns a flow of [ResultState].
     * Emits [ResultState.LOADING] before fetching data and [ResultState.SUCCESS] or [ResultState.ERROR]
     * based on the success of the fetch operation.
     * The flow runs on [ioDispatcher] to perform the fetch operation on a background thread.
     */
    override fun getCharacters(type: String): Flow<ResultState<List<DomainCharacter>>> = flow {
        emit(ResultState.LOADING)
        try {
            val response = serviceApi.getCharacters(type)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultState.SUCCESS(it.relatedTopics.mapToDomainCharacters()))
                } ?: throw Exception("No response")
            } else {
                throw Exception(response.errorBody().toString())
            }
        } catch (e: Exception) {
            emit(ResultState.ERROR(e))
        }
    }.flowOn(ioDispatcher)

}
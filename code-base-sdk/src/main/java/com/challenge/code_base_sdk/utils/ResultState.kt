package com.challenge.code_base_sdk.utils

/**
 * Sealed class to represent the different states of a result.
 * It can either be LOADING, SUCCESS with data, or ERROR with an exception.
 * @param T The type of data being returned.
 */
sealed class ResultState<out T>{
    object LOADING : ResultState<Nothing>()
    data class SUCCESS<T>(val data: T): ResultState<T>()
    data class ERROR(val error: Exception): ResultState<Nothing>()
}

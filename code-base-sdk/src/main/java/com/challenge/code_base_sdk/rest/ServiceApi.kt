package com.challenge.code_base_sdk.rest

import com.challenge.code_base_sdk.model.CharacterResponse
import com.challenge.code_base_sdk.utils.JSON_FORMAT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    /**
     * This method sends an HTTP GET request to the server to retrieve characters of a specified type.
     * @param type The type of characters to retrieve.
     * @param format The format of the response (defaults to JSON).
     * @return A `Response` object wrapping a `CharacterResponse` object.
     */
    @GET("/")
    suspend fun getCharacters(
        @Query("q") type: String,
        @Query("format") format: String = JSON_FORMAT
    ): Response<CharacterResponse>

}
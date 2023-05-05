package com.challenge.code_base_sdk.model.domain

import com.challenge.code_base_sdk.model.RelatedTopic
import com.challenge.code_base_sdk.utils.IMAGE_URL
import java.util.regex.Pattern

data class DomainCharacter(
    val name: String,
    val description: String,
    val image: String? = null
)

fun List<RelatedTopic>?.mapToDomainCharacters(): List<DomainCharacter> {


    return this?.map {

        val item = it.text?.split("-")
        DomainCharacter(
            name = item?.get(0) ?: "Name was empty",
            description = it.text ?: "No description",
            image = it.icon?.uRL?.run { "$IMAGE_URL${this}" }
        )
    } ?: emptyList()

}


package com.challenge.code_base_sdk.model


import com.google.gson.annotations.SerializedName

data class Maintainer(
    @SerializedName("github")
    val github: String? = null
)
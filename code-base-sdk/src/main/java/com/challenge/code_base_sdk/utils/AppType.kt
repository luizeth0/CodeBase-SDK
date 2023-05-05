package com.challenge.code_base_sdk.utils

/*
This is the class that will drive the type of data needed
if we require to support more items, we need to
add the new ENUMS to this class
 */
enum class AppType(val endPoint: String) {

    SIMPSONS("simpsons characters"),
    THE_WIRE("the wire characters")

}

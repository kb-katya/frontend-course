package model

import kotlinx.serialization.Serializable

@Serializable
class Balance (
    val id: Int,
    var sum : Int,
    val name: String,
    val userId: Int
)
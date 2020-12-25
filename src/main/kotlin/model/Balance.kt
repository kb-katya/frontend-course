package model

import kotlinx.serialization.Serializable

@Serializable
class Balance (
    val id: Int,
    var sum : Int,
    val name: String,
    val userId: Int
)

@Serializable
data class BalancePayload (
    var sum : Int,
    val name: String,
    val userId: Int
)

@Serializable
data class TransferBalancePayload (
    var id1 : Int,
    val id2: Int,
    val sum: Int
)
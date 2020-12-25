package model

import kotlinx.serialization.Serializable

@Serializable
class Credit(
    val id: Int,
    var sumCredit: Int,
    var balance: Int,
    val name : String,
    val userId: Int
)

@Serializable
class CreditPayload(
    var sumCredit: Int,
    var balance: Int,
    val name : String,
    val userId: Int
)

@Serializable
data class PayCreditPayload (
    var id1 : Int,
    val id2: Int,
    val sum: Int
)
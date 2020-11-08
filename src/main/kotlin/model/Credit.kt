package model

import kotlin.js.Date

data class Credit(
    val payerId: Int,
    val amountOfCredit: Double,
    val dateReceiving: Date = Date(),
    val dataPayment: Date = Date(),
    val percent: Double,
    val abilityToPay: Boolean
);
package model

import kotlin.js.Date

data class Card(
    val number: Int,
    val fullName: String,
    val cvc: Int,
    val validity: Date = Date(), val percent: Double,
    val payerId: Int
);
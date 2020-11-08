package model

import kotlin.js.Date

data class Operation(
    val date: Date = Date(),
    val operationTypeId: Int,
    val payerId: Int
);
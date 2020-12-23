package data

import model.*

typealias CreditListState = Map<Int, Credit>

typealias BalanceListState = Map<Int, Balance>

typealias ActiveUserState = Pair<User, String>?


data class State(
    val creditList: CreditListState,
    val balanceList: BalanceListState,
    val activeAccount: ActiveUserState
)

fun <T> Map<Int, T>.newId() =
    (this.maxBy { it.key }?.key ?: 0) + 1

fun initialState() =
    State(
        mapOf(),
        mapOf(),
        null
    )
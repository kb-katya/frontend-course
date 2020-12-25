package data

import model.*

typealias CreditListState = Map<Int, Credit>

typealias BalanceListState = Map<Int, Balance>

typealias ActiveUserState = Pair<Int, User>?


data class State(
    val creditList: CreditListState,
    val balanceList: BalanceListState,
    val activeUser: ActiveUserState
)

fun initialState() =
    State(
        mapOf(),
        mapOf(),
        null
    )
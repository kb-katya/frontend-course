package redux

import data.*

fun creditReducer(state: CreditListState = mapOf(), action: RAction) =
    when (action) {
        else -> state
    }

fun balanceReducer(state: BalanceListState = mapOf(), action: RAction) =
    when (action) {
        else -> state
    }

fun activeUserReducer(state: ActiveUserState = null, action: RAction) =
    when (action) {
        is SetActiveUser -> action.user
        else -> state
    }

fun rootReducer(state: State, action: RAction) =
    State(
        creditReducer(state.creditList, action),
        balanceReducer(state.balanceList, action),
        activeUserReducer(state.activeUser, action)
    )
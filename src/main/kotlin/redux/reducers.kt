package redux

import data.*

fun operationReducer(state: OperationListState = mapOf(), action: RAction) =
    when (action) {
        else -> state
    }

fun creditReducer(state: CreditListState = mapOf(), action: RAction) =
    when (action) {
        else -> state
    }

fun cardReducer(state: CardListState = mapOf(), action: RAction) =
    when (action) {
        else -> state
    }

fun payerReducer(state: PayerListState = mapOf(), action: RAction) =
    when (action) {
        else -> state
    }

fun employeeReducer(state: EmployeeListState = mapOf(), action: RAction) =
    when (action) {
        else -> state
    }

fun activeAccountReducer(state: ActiveEmployeeState = null, action: RAction) =
    when (action) {
        is SetActiveAccount -> action.account
        else -> state
    }

fun rootReducer(state: State, action: RAction) =
    State(
        operationReducer(state.operationList, action),
        creditReducer(state.creditList, action),
        cardReducer(state.cardList, action),
        payerReducer(state.payerList, action),
        employeeReducer(state.employeeList, action),
        activeAccountReducer(state.activeAccount, action)
    )
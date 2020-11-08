package data

import model.*

typealias OperationListState = Map<Int, Operation>

typealias CreditListState = Map<Int, Credit>

typealias CardListState = Map<Int, Card>

typealias EmployeeListState = Map<Int, Employee>

typealias PayerListState = Map<Int, Payer>

typealias ActiveEmployeeState = Pair<Int, Employee>?


data class State(
    val operationList: OperationListState,
    val creditList: CreditListState,
    val cardList: CardListState,
    val payerList: PayerListState,
    val employeeList: EmployeeListState,
    val activeAccount: ActiveEmployeeState
)

fun <T> Map<Int, T>.newId() =
    (this.maxBy { it.key }?.key ?: 0) + 1

val employeeList = mapOf(
    0 to Admin(0, "Admin",  "Adminov"),
    1 to Operator(1, "Operator",  "Operatorov"),
    2 to Operator(2, "Consultant",  "Consultantov")
)

fun initialState() =
    State(
        mapOf(),
        mapOf(),
        mapOf(),
        mapOf(),
        employeeList,
        null
    )
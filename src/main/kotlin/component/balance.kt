package component

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import react.*
import react.dom.*
import kotlinx.html.id
import model.*
import model.Balance as BalanceItem
import react.router.dom.navLink
import services.Request
import services.backendUrl
import services.scope

interface BalanceProps : RProps {
    var balances: Map<Int, BalanceItem>
    var account: Pair<String, User>
    var setBalances: (Map<Int, BalanceItem>) -> Unit
}

interface BalanceState : RState {
    var isLoading: Boolean
}

class Balance : RComponent<BalanceProps, BalanceState>() {

    init {
        state.apply {
            isLoading = true
        }
    }

    private suspend fun fetchBalance(): List<BalanceItem> {
        return try {
            Request.get {
                url("$backendUrl/user/${props.account.second.id}/balances")
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer ${props.account.first}")
                accept(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            console.error("Ошибка при получении счетов")
            listOf()
        }
    }

    override fun componentDidMount() {
        scope.launch {
            val result = fetchBalance().map { credit -> credit.id to credit }
            props.setBalances(result.toMap())
            setState {
                isLoading = false
            }
        }
    }

    override fun RBuilder.render() {
        div {
            attrs.id = "balance"
            h1 {
                +"Счета"
            }
            if (state.isLoading)
                div("spin-wrapper") {
                    i("fa fa-spinner fa-spin") {}
                }
            else
                table {
                    thead {
                        tr {
                            th {
                                +"Индификатор"
                            }
                            th {
                                +"Наименование"
                            }
                            th {
                                +"Состояние счета"
                            }
                            th {
                                +"Перевести на другой счет"
                            }
                        }
                    }
                    tbody {
                        props.balances.map {
                            tr {
                                td {
                                    +it.key.toString()
                                }
                                td {
                                    +it.value.name
                                }
                                td {
                                    +it.value.sum.toString()
                                }
                                td {
                                    navLink<RProps>("/balance/${it.key}") {
                                        i("fa fa-exchange") {}
                                    }
                                }
                            }
                        }
                    }
                }
            navLink<RProps>("/balance/create-new") {
                button {
                    +"Добавить новый счет"
                }
            }
        }
    }
}

fun RBuilder.credit(
    balances: Map<Int, BalanceItem>,
    account: Pair<String, User>,
    setBalances: (Map<Int, BalanceItem>) -> Unit
) = child(Balance::class) {
    attrs.balances = balances
    attrs.account = account
    attrs.setBalances = setBalances
}
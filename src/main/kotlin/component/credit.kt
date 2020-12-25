package component

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import react.*
import react.dom.*
import kotlinx.html.id
import model.*
import model.Credit as CreditItem
import react.router.dom.navLink
import services.Request
import services.backendUrl
import services.scope

interface CreditProps : RProps {
    var credits: Map<Int, CreditItem>
    var account: Pair<String, User>
    var setCredits: (Map<Int, CreditItem>) -> Unit
}

interface CreditState : RState {
    var isLoading: Boolean
}

class Credit : RComponent<CreditProps, CreditState>() {

    init {
        state.apply {
            isLoading = true
        }
    }

    private suspend fun fetchCredits(): List<CreditItem> {
        return try {
            Request.get {
                url("$backendUrl/user/${props.account.second.id}/credits")
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer ${props.account.first}")
                accept(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            console.error("Ошибка при получении кредитов")
            listOf()
        }
    }

    override fun componentDidMount() {
        scope.launch {
           val result = fetchCredits().map { credit -> credit.id to credit }.toMap()
           props.setCredits(result)
           setState {
               isLoading = false
           }
        }
    }

    override fun RBuilder.render() {
        div {
            attrs.id = "credit"
            h1 {
                +"Кредиты"
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
                                +"Остаток по кредиту"
                            }
                            th {
                                +"Баланс кредита"
                            }
                            th {
                                +"Оплатить кредит"
                            }
                            th {
                                +"Снять деньги"
                            }
                        }
                    }
                    tbody {
                        props.credits.map {
                            tr {
                                td {
                                    +it.key.toString()
                                }
                                td {
                                    +it.value.name
                                }
                                td {
                                    +it.value.sumCredit.toString()
                                }
                                td {
                                    +it.value.balance.toString()
                                }
                                td {
                                    navLink<RProps>("/credit/${it.key}") {
                                        i("fa fa-credit-card") {}
                                    }
                                }
                                td {
                                    navLink<RProps>("/credit/${it.key}/transfer") {
                                        i("fa fa-money") {}
                                    }
                                }
                            }
                        }
                    }
                }
            navLink<RProps>("/credit/create-new") {
                button {
                    +"Взять кредит"
                }
            }
        }
    }
}

fun RBuilder.credit(
    credits: Map<Int, CreditItem>,
    account: Pair<String, User>,
    setCredits: (Map<Int, CreditItem>) -> Unit
) = child(Credit::class) {
    attrs.credits = credits
    attrs.account = account
    attrs.setCredits = setCredits
}
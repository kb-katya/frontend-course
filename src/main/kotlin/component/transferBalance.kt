package component

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinext.js.asJsObject
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import react.*
import react.dom.*
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import model.*
import model.Balance
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import services.Request
import services.backendUrl
import services.scope

interface TransferBalanceProps : RProps {
    var obj: Pair<Int, Balance>
    var balances: Map<Int, Balance>
    var account: Pair<String, User>
}

interface TransferBalanceState : RState {
    var id: String
    var sum: String
}

class TransferBalance : RComponent<TransferBalanceProps, TransferBalanceState>() {

    init {
        state.apply {
            id = ""
            sum = ""
        }
    }

    override fun componentDidMount() {
        setState {
            id = props.balances.toList().first().first.toString()
        }
    }

    private suspend fun transferBalance(): HttpStatusCode {
        return Request.put {
            url("$backendUrl/user/${props.account.second.id}/balance-transfer")
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${props.account.first}")
            accept(ContentType.Application.Json)
            body = TransferBalancePayload(id1 = props.obj.first, id2 = state.id.toInt(), sum = state.sum.toInt())
        }
    }

    private fun getTarget(event: Event) = event.target?.asJsObject().unsafeCast<HTMLInputElement>()

    override fun RBuilder.render() {
        div {
            attrs.id = "balance"
            h1 {
                +"Перевод между счетами"
            }
            h3 {
                +"Перевести деньги со счета: ${props.obj.first}"
            }
            h4 {
                +"Имя счета: ${props.obj.second.name}"
            }
            h4 {
                +"Баланс: ${props.obj.second.sum}"
            }
            h3 {
                +"На счет:"
            }
            select {
                props.balances.map {
                    option {
                        +"${it.key}: ${it.value.name} | ${it.value.sum}"
                        attrs.value = it.key.toString()
                    }
                    attrs.onChangeFunction = {
                        val target = getTarget(it)
                        setState {
                            id = target.value
                        }
                    }
                }
            }
            field("Cумма", state.sum,"", InputType.number) {
                val target = getTarget(it)
                setState { sum = target.value }
            }
            button {
                +"Перевести"
                attrs.onClickFunction = {
                    scope.launch {
                        transferBalance()
                    }
                }
            }
        }
    }
}

fun RBuilder.transferBalance(
    obj: Pair<Int, Balance>,
    balances: Map<Int, Balance>,
    account: Pair<String, User>
) = child(TransferBalance::class) {
    attrs.account = account
    attrs.balances = balances
    attrs.obj = obj
}
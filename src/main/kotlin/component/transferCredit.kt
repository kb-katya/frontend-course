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
import model.Credit
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import services.Request
import services.backendUrl
import services.scope

interface TransferCreditProps : RProps {
    var obj: Pair<Int, Credit>
    var balances: Map<Int, Balance>
    var account: Pair<String, User>
}

interface TransferCreditState : RState {
    var id: String
    var sum: String
}

class TransferCredit : RComponent<TransferCreditProps, TransferCreditState>() {

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

    private suspend fun transferCredit(): HttpStatusCode {
        return Request.put {
            url("$backendUrl/user/${props.account.second.id}/credit/${props.obj.first}")
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${props.account.first}")
            accept(ContentType.Application.Json)
            body = PayCreditPayload(id1 = props.obj.first, id2 = state.id.toInt(), sum = state.sum.toInt())
        }
    }

    private fun getTarget(event: Event) = event.target?.asJsObject().unsafeCast<HTMLInputElement>()

    override fun RBuilder.render() {
        div {
            attrs.id = "credit"
            h1 {
                +"Снять деньги и перевести на счет"
            }
            h3 {
                +"Кредит: ${props.obj.first}"
            }
            h4 {
                +"Имя кредита: ${props.obj.second.name}"
            }
            h4 {
                +"Баланс кредита: ${props.obj.second.balance}"
            }
            h3 {
                +"Пополнить счет:"
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
                +"Пополнить"
                attrs.onClickFunction = {
                    scope.launch {
                        transferCredit()
                    }
                }
            }
        }
    }
}

fun RBuilder.transferCredit(
    obj: Pair<Int, Credit>,
    balances: Map<Int, Balance>,
    account: Pair<String, User>
) = child(TransferCredit::class) {
    attrs.account = account
    attrs.balances = balances
    attrs.obj = obj
}
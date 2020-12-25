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

interface PayCreditProps : RProps {
    var obj: Pair<Int, Credit>
    var balances: Map<Int, Balance>
    var account: Pair<String, User>
}

interface PayCreditState : RState {
    var id: String
    var sum: String
}

class PayCredit : RComponent<PayCreditProps, PayCreditState>() {

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

    private suspend fun payCredit(): HttpStatusCode {
        return Request.put {
            url("$backendUrl/user/${props.account.second.id}/pay-credit")
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
                +"Оплатить кредит"
            }
            h3 {
                +"Оплатить кредит: ${props.obj.first}"
            }
            h4 {
                +"Имя кредита: ${props.obj.second.name}"
            }
            h4 {
                +"Остаток по кредиту: ${props.obj.second.sumCredit}"
            }
            h3 {
                +"Со счета:"
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
                +"Оплатить"
                attrs.onClickFunction = {
                    scope.launch {
                        payCredit()
                    }
                }
            }
        }
    }
}

fun RBuilder.payCredit(
    obj: Pair<Int, Credit>,
    balances: Map<Int, Balance>,
    account: Pair<String, User>
) = child(PayCredit::class) {
    attrs.account = account
    attrs.balances = balances
    attrs.obj = obj
}
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
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import services.Request
import services.backendUrl
import services.scope

interface CreateBalanceProps : RProps {
    var account: Pair<String, User>
}

interface CreateBalanceState : RState {
    var name: String
    var sum: String
}

class CreateBalance : RComponent<CreateBalanceProps, CreateBalanceState>() {

    init {
        state.apply {
            name = ""
            sum = ""
        }
    }

    private suspend fun createBalance(): HttpStatusCode {
        return Request.post {
            url("$backendUrl/user/${props.account.second.id}/balances")
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${props.account.first}")
            accept(ContentType.Application.Json)
            body = BalancePayload(sum = state.sum.toInt(), name = state.name, userId = props.account.second.id)
        }
    }

    private fun getTarget(event: Event) = event.target?.asJsObject().unsafeCast<HTMLInputElement>()

    override fun RBuilder.render() {
        div {
            attrs.id = "balance"
            h1 {
                +"Добавить новый счет"
            }
            field("Наименование", state.name,"", InputType.text) {
                val target = getTarget(it)
                setState { name = target.value }
            }
            field("Сумма на счету", state.sum, "", InputType.number) {
                val target = getTarget(it)
                setState { sum = target.value }
            }
            button {
                +"Добавить"
                attrs.onClickFunction = {
                    scope.launch {
                        createBalance()
                    }
                }
            }
        }
    }
}

fun RBuilder.createBalance(
    account: Pair<String, User>,
) = child(CreateBalance::class) {
    attrs.account = account
}
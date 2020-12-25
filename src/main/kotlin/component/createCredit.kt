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

interface CreateCreditProps : RProps {
    var account: Pair<String, User>
}

interface CreateCreditState : RState {
    var name: String
    var sum: String
}

class CreateCredit : RComponent<CreateCreditProps, CreateCreditState>() {

    init {
        state.apply {
            name = ""
            sum = ""
        }
    }

    private suspend fun createCredit(): HttpStatusCode {
        val value = state.sum.toInt()
        return Request.post {
            url("$backendUrl/user/${props.account.second.id}/credits")
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${props.account.first}")
            accept(ContentType.Application.Json)
            body = CreditPayload(sumCredit = value + (value * 0.15).toInt(), balance = value, name = state.name, userId = props.account.second.id)
        }
    }

    private fun getTarget(event: Event) = event.target?.asJsObject().unsafeCast<HTMLInputElement>()

    override fun RBuilder.render() {
        div {
            attrs.id = "credit"
            h1 {
                +"Взять кредит"
            }
            field("Наименование", state.name,"", InputType.text) {
                val target = getTarget(it)
                setState { name = target.value }
            }
            field("Сумма кредита", state.sum, "", InputType.number) {
                val target = getTarget(it)
                setState { sum = target.value }
            }
            button {
                +"Взять кредит"
                attrs.onClickFunction = {
                    scope.launch {
                        createCredit()
                    }
                }
            }
        }
    }
}

fun RBuilder.createCredit(
    account: Pair<String, User>,
) = child(CreateCredit::class) {
    attrs.account = account
}
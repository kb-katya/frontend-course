package component

import data.ActiveUserState
import react.*
import react.dom.*
import kotlinx.html.js.onClickFunction
import model.*
import org.w3c.dom.events.Event

interface AccountProps : RProps {
    var account: ActiveUserState
    var logout: (Event) -> Unit
}

val fAccount =
    functionalComponent<AccountProps> { props ->
        val accountId = props.account!!.first.id
        val account = props.account!!.first
        div("account") {
            h1 {
                +"Личный кабинет"
            }
            div("account-info") {
                label {
                    strong {
                        +"Код аккаунта: "
                    }
                    +accountId.toString()
                }
                label {
                    strong {
                        +"Имя: "
                    }
                    +account.name
                }
                button {
                    attrs.onClickFunction = props.logout
                    +"Выйти из аккаунта"
                }
            }
        }
    }

fun RBuilder.account(
    account: ActiveUserState,
    logout: (Event) -> Unit
) = child(fAccount) {
    attrs.account = account
    attrs.logout = logout
}
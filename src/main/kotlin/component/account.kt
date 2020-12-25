package component

import react.*
import react.dom.*
import kotlinx.html.js.onClickFunction
import model.*
import org.w3c.dom.events.Event

interface AccountProps : RProps {
    var account: Pair<Int, User>
    var logout: (Event) -> Unit
}

val fAccount =
    functionalComponent<AccountProps> { props ->
        val accountId = props.account.first
        val account = props.account.second
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
    account: Pair<Int, User>,
    logout: (Event) -> Unit
) = child(fAccount) {
    attrs.account = account
    attrs.logout = logout
}
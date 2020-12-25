package component

import data.ActiveUserState
import react.*
import react.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event

interface AccountProps : RProps {
    var account: ActiveUserState
    var logout: (Event) -> Unit
}

val fAccount =
    functionalComponent<AccountProps> { props ->
        val account = props.account!!.second
        div("account") {
            h1 {
                +"Личный кабинет"
            }
            div("account-info") {
                h3 {
                    strong {
                        +"Код аккаунта: "
                    }
                    +account.id.toString()
                }
                h3 {
                    strong {
                        +"Почта: "
                    }
                    +account.email
                }
                h3 {
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
package component

import react.*
import react.dom.*
import kotlinx.html.js.onClickFunction
import model.*
import org.w3c.dom.events.Event

interface AccountProps : RProps {
    var account: Pair<Int, Employee>
    var logout: (Event) -> Unit
}

val fAccount =
    functionalComponent<AccountProps> { props ->
        val accountId = props.account.first
        val account = props.account.second
        val accountType = if (account is Admin) "Админ"
            else if (account is Operator) "Оператор"
            else if (account is Consultant) "Консультант"
            else ""
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
                label {
                    strong {
                        +"Фамилия: "
                    }
                    +account.surname
                }
                label {
                    strong {
                        +"Роль: "
                    }
                    +accountType
                }
                button {
                    attrs.onClickFunction = props.logout
                    +"Выйти из аккаунта"
                }
            }
        }
    }

fun RBuilder.account(
    account: Pair<Int, Employee>,
    logout: (Event) -> Unit
) = child(fAccount) {
    attrs.account = account
    attrs.logout = logout
}
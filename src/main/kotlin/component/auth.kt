package component

import kotlinext.js.asJsObject
import kotlinx.html.InputType
import react.*
import react.dom.*
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import model.Employee
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event

interface AuthProps : RProps {
    var accountList: Map<Int, Employee>
    var login: (Pair<Int, Employee>) -> (Event) -> Unit
}

interface AuthState : RState {
    var signInLogin: String
    var signInPassword: String
}

class Auth : RComponent<AuthProps, AuthState>() {

    init {
        state.apply {
            signInLogin = ""
            signInPassword = ""
        }
    }

    fun getTarget(event: Event) = event.target?.asJsObject().unsafeCast<HTMLInputElement>()

    fun RBuilder.field(label: String, value: String, placeholder: String = "", type: InputType, onChange: (Event) -> Unit) {
        label {
            +label
        }
        input {
            attrs.value = value
            attrs.onChangeFunction = onChange
            attrs.placeholder = placeholder
            attrs.type = type
        }
    }

    val onClickLogin = {
        val account = props.accountList.filterValues {
            it.login == state.signInLogin && it.password == state.signInPassword
        }
        val onClickLoginError: (Event) -> Unit = {
            console.error("Аккаунт не найден!")
        }
        if (account.isNotEmpty())
            props.login(account.keys.first() to account[account.keys.first()]!!)
        else
            onClickLoginError
    }

    fun RBuilder.signIn() {
        div {
            h2 {
                +"Войти в аккаунт"
            }
            field("Логин", state.signInLogin,"", InputType.text) {
                val target = getTarget(it)
                setState { signInLogin = target.value }
            }
            field("Пароль", state.signInPassword, "", InputType.password) {
                val target = getTarget(it)
                setState { signInPassword = target.value }
            }
            input {
                attrs.type = InputType.button
                attrs.value = "Войти"
                attrs.onClickFunction = onClickLogin()
            }
        }
    }

    override fun RBuilder.render() {
        div {
            attrs.id = "auth"
            signIn()
        }
    }
}

fun RBuilder.auth(
    accountList: Map<Int, Employee>,
    login: (Pair<Int, Employee>) -> (Event) -> Unit
) = child(Auth::class) {
    attrs.accountList = accountList
    attrs.login = login
}
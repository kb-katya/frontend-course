package component

import data.ActiveUserState
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
import services.backendUrl
import services.Request
import services.scope

interface AuthProps : RProps {
    var login: (ActiveUserState) -> (Event) -> Unit
}

interface AuthState : RState {
    var email: String
    var password: String
    var name: String
    var isLogin: Boolean
}

class Auth : RComponent<AuthProps, AuthState>() {

    init {
        state.apply {
            email = ""
            password = ""
            name = ""
            isLogin = true
        }
    }

    private fun getTarget(event: Event) = event.target?.asJsObject().unsafeCast<HTMLInputElement>()

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

    private suspend fun onClickLogin() {
        try {
            val obj = Request.post<UserWithToken> {
                url("${backendUrl}/users/login")
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                body = LoginUserPayload(state.email, state.password)
            }
            props.login(obj.user to obj.token)
        }
        catch (e: Exception) {
            console.error("Аккаунт не найден!")
        }
    }

    private suspend fun onClickSignUp() {
        try {
            Request.post<User> {
                url("${backendUrl}/users/registry")
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                body = SignUpUserPayload(state.name, state.email, state.password)
            }
            setState {
                isLogin = true
                password = ""
                email = ""
                name = ""
            }
        }
        catch (e: Exception) {
            console.error("Ошибка при создании аккаунта!")
        }
    }

    private fun RBuilder.signIn() {
        val title = if (state.isLogin) "Создать аккаунт" else "Войти в аккаунт"
        val buttonText = if (!state.isLogin) "Создать" else "Войти"
        div {
            h2 {
                +title
            }
            if (!state.isLogin)
                field("Имя", state.name,"", InputType.text) {
                    val target = getTarget(it)
                    setState { name = target.value }
                }
            field("Почта", state.email,"", InputType.email) {
                val target = getTarget(it)
                setState { email = target.value }
            }
            field("Пароль", state.password, "", InputType.password) {
                val target = getTarget(it)
                setState { password = target.value }
            }
            input {
                attrs.type = InputType.button
                attrs.value = buttonText
                attrs.onClickFunction = {
                    it.preventDefault()
                    scope.launch {
                        if (!state.isLogin) onClickSignUp() else onClickLogin()
                    }
                }
            }
            input {
                attrs.type = InputType.button
                attrs.value = title
                attrs.onClickFunction = {
                    setState { isLogin = !isLogin }
                }
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
    login: (ActiveUserState) -> (Event) -> Unit
) = child(Auth::class) {
    attrs.login = login
}
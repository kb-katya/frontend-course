package component

import container.*
import data.*
import react.*
import react.dom.p
import react.dom.section
import react.router.dom.RouteResultProps
import react.router.dom.route
import react.router.dom.switch

interface AppProps: RProps {
    var activeAccount: ActiveUserState
}

interface RouteNumberResult : RProps {
    var number: String
}

fun fApp() =
    functionalComponent<AppProps> { props ->
        navbar(navItemList.filter {
            if (props.activeAccount == null)
                it.title != "Личный кабинет"
            else
                it.title != "Войти в аккаунт"
        })
        section {
            switch {
                route("/account",
                    exact = true,
                    render = {
                        if (props.activeAccount == null)
                            authContainer { }
                        else
                            accountContainer { }
                    }
                )
            }
        }
    }

fun <O> RBuilder.renderObject(
    selector: (Int) -> O?,
    rElement: (Int, O) -> ReactElement
) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val obj = selector(num)
        if (obj != null)
            rElement(num, obj)
        else
            p { +"Страница не найдена!" }
    }

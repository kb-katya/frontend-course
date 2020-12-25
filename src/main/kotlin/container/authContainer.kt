package container

import react.*
import component.*
import data.ActiveUserState
import data.State
import react.redux.rConnect
import redux.RAction
import redux.SetActiveAccount
import redux.WrapperAction

interface AuthDispatchProps : RProps {
    var login: (ActiveUserState) -> Unit
}

interface AuthStateProps : RProps

val authContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            RProps,
            AuthStateProps,
            AuthDispatchProps,
            AuthProps
            >(
        { _, _ -> },
        { dispatch, _ ->
            login = { account -> dispatch(SetActiveAccount(account)) }
        }
    )(
        Auth::class.js.unsafeCast<RClass<AuthProps>>()
    )
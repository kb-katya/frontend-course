package container

import react.*
import component.*
import data.State
import model.User
import react.redux.rConnect
import org.w3c.dom.events.Event
import redux.RAction
import redux.SetActiveUser
import redux.WrapperAction

interface AuthDispatchProps : RProps {
    var login: (Pair<Int, User>) -> (Event) -> Unit
}

interface AuthStateProps : RProps {
}

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
        { state, _ ->
        },
        { dispatch, _ ->
            login = { account ->
                {
                    dispatch(SetActiveUser(account))
                }
            }
        }
    )(
        Auth::class.js.unsafeCast<RClass<AuthProps>>()
    )
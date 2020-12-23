package container

import react.*
import component.*
import data.ActiveUserState
import data.State
import react.redux.rConnect
import hoc.withDisplayName
import org.w3c.dom.events.Event
import redux.RAction
import redux.WrapperAction
import redux.SetActiveAccount

interface AccountDispatchProps : RProps {
    var logout: (Event) -> Unit
}

interface AccountStateProps : RProps {
    var account: ActiveUserState
}

val accountContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            RProps,
            AccountStateProps,
            AccountDispatchProps,
            AccountProps
            >(
        { state, _ ->
            account = state.activeAccount!!
        },
        { dispatch, _ ->
            logout = {
                dispatch(SetActiveAccount(null))
            }
        }
    )(
        withDisplayName(
            "Account",
            fAccount
        )
            .unsafeCast<RClass<AccountProps>>()
    )
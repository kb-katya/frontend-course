package container

import react.*
import component.*
import data.State
import react.redux.rConnect
import hoc.withDisplayName
import model.User
import org.w3c.dom.events.Event
import redux.RAction
import redux.WrapperAction
import redux.SetActiveUser

interface AccountDispatchProps : RProps {
    var logout: (Event) -> Unit
}

interface AccountStateProps : RProps {
    var account: Pair<Int, User>
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
            account = state.activeUser!!
        },
        { dispatch, _ ->
            logout = {
                dispatch(SetActiveUser(null))
            }
        }
    )(
        withDisplayName(
            "Account",
            fAccount
        )
            .unsafeCast<RClass<AccountProps>>()
    )
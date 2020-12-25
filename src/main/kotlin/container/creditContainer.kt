package container

import model.Credit as CreditItem
import react.*
import component.*
import data.ActiveUserState
import data.CreditListState
import data.State
import react.redux.rConnect
import redux.RAction
import redux.SetCredits
import redux.WrapperAction

interface CreditDispatchProps : RProps {
    var setCredits: (Map<Int, CreditItem>) -> Unit
}

interface CreditStateProps : RProps {
    var credits: CreditListState
    var account: ActiveUserState
}

val creditContainer =
    rConnect<
        State,
        RAction,
        WrapperAction,
        RProps,
        CreditStateProps,
        CreditDispatchProps,
        CreditProps
    >(
        { state, _ ->
            credits = state.creditList
            account = state.activeAccount
        },
        { dispatch, _ ->
            setCredits = { credits -> dispatch(SetCredits(credits))}
        }
    )(
        Credit::class.js.unsafeCast<RClass<CreditProps>>()
    )
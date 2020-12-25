package container

import react.*
import component.*
import data.ActiveUserState
import data.State
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

interface ActiveAccountDispatchProps : RProps

interface ActiveAccountStateProps : RProps {
    var account: ActiveUserState
}

val createBalanceContainer =
    rConnect<
        State,
        RAction,
        WrapperAction,
        RProps,
        ActiveAccountStateProps,
        ActiveAccountDispatchProps,
        CreateBalanceProps
    >(
        { state, _ ->
            account = state.activeAccount!!
        },
        { dispatch, _ -> }
    ) (
        CreateBalance::class.js.unsafeCast<RClass<CreateBalanceProps>>()
    )

val createCreditContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            RProps,
            ActiveAccountStateProps,
            ActiveAccountDispatchProps,
            CreateCreditProps
            >(
        { state, _ ->
            account = state.activeAccount!!
        },
        { dispatch, _ -> }
    ) (
        CreateCredit::class.js.unsafeCast<RClass<CreateCreditProps>>()
    )
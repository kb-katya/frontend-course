package container

import model.Balance as BalanceItem
import react.*
import component.*
import data.ActiveUserState
import data.BalanceListState
import data.State
import react.redux.rConnect
import redux.RAction
import redux.SetBalances
import redux.WrapperAction

interface BalanceDispatchProps : RProps {
    var setBalances: (Map<Int, BalanceItem>) -> Unit
}

interface BalanceStateProps : RProps {
    var balances: BalanceListState
    var account: ActiveUserState
}

val balanceContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            RProps,
            BalanceStateProps,
            BalanceDispatchProps,
            BalanceProps
            >(
        { state, _ ->
            balances = state.balanceList
            account = state.activeAccount
        },
        { dispatch, _ ->
            setBalances = { balances -> dispatch(SetBalances(balances))}
        }
    )(
        Balance::class.js.unsafeCast<RClass<BalanceProps>>()
    )
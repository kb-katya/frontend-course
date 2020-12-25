package container

import react.*
import component.*
import data.BalanceListState
import data.State
import model.Balance as BalanceItem
import model.Credit as CreditItem
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

interface AnyFullStateProps<O> : RProps {
    var obj: O
}

interface TransferBalanceStateProps<BalanceItem> : ActiveAccountStateProps {
    var balances: BalanceListState
    var obj: Pair<Int, BalanceItem>
}

val transferBalanceContainer =
    rConnect<
        State,
        RAction,
        WrapperAction,
        AnyFullStateProps<BalanceItem>,
        TransferBalanceStateProps<BalanceItem>,
        RProps,
        TransferBalanceProps
    >(
        { state, ownProps ->
            account = state.activeAccount!!
            balances = state.balanceList.filter { it.key != ownProps.obj.id }
            obj = ownProps.obj.id to ownProps.obj
        },
        { dispatch, _ -> }
    ) (
        TransferBalance::class.js.unsafeCast<RClass<TransferBalanceProps>>()
    )

interface PayCreditsStateProps<CreditItem>: ActiveAccountStateProps {
    var balances: BalanceListState
    var obj: Pair<Int, CreditItem>
}

val payCreditContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            AnyFullStateProps<CreditItem>,
            PayCreditsStateProps<CreditItem>,
            RProps,
            PayCreditProps
            >(
        { state, ownProps ->
            account = state.activeAccount!!
            balances = state.balanceList
            obj = ownProps.obj.id to ownProps.obj
        },
        { dispatch, _ -> }
    ) (
        PayCredit::class.js.unsafeCast<RClass<PayCreditProps>>()
    )

val transferCreditContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            AnyFullStateProps<CreditItem>,
            PayCreditsStateProps<CreditItem>,
            RProps,
            TransferCreditProps
            >(
        { state, ownProps ->
            account = state.activeAccount!!
            balances = state.balanceList
            obj = ownProps.obj.id to ownProps.obj
        },
        { dispatch, _ -> }
    ) (
        TransferCredit::class.js.unsafeCast<RClass<TransferCreditProps>>()
    )
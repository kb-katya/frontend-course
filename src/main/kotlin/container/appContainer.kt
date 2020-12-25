package container

import react.*
import component.*
import data.State
import react.redux.rConnect
import hoc.withDisplayName

val appContainer =
    rConnect<State, RProps, AppProps>(
        { state, _ ->
            activeAccount = state.activeAccount
            credits = state.creditList
            balances = state.balanceList
        },
        {
            pure = false  // side effect of React Route
        }
    )(
        withDisplayName(
            "App",
            fApp()
        )
            .unsafeCast<RClass<AppProps>>()
    )
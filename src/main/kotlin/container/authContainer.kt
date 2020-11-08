package container

import react.*
import component.*
import data.EmployeeListState
import data.State
import model.Employee
import react.redux.rConnect
import org.w3c.dom.events.Event
import redux.RAction
import redux.SetActiveAccount
import redux.WrapperAction

interface AuthDispatchProps : RProps {
    var login: (Pair<Int, Employee>) -> (Event) -> Unit
}

interface AuthStateProps : RProps {
    var accountList: EmployeeListState
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
            accountList = state.employeeList
        },
        { dispatch, _ ->
            login = { account ->
                {
                    dispatch(SetActiveAccount(account))
                }
            }
        }
    )(
        Auth::class.js.unsafeCast<RClass<AuthProps>>()
    )
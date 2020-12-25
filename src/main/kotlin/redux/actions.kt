package redux

import data.ActiveUserState
import data.BalanceListState
import data.CreditListState

class SetActiveAccount(val account: ActiveUserState) : RAction

class SetCredits(val credits: CreditListState) : RAction

class SetBalances(val balances: BalanceListState) : RAction
package data

data class NavItem (
    val title: String,
    val path: String,
    val isAuth: Boolean = false
)

val navItemList =
    arrayListOf(
        NavItem("Личный кабинет", "/account"),
        NavItem("Войти в аккаунт", "/account"),
        NavItem("Счета", "/balance", true),
        NavItem("Кредиты", "/credit", true),
)
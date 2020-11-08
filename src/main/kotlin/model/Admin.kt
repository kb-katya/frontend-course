package model

data class Admin (
    override var id: Int = 0,
    override var name: String = "",
    override var surname: String = "",
    override var login: String = "Admin",
    override var password: String = "AdminPassword"
) : Employee()
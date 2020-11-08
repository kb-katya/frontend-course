package model

data class Operator (
    override var id: Int = 0,
    override var name: String = "",
    override var surname: String = "",
    override var login: String = "Operator",
    override var password: String = "OperatorPassword"
) : Employee()
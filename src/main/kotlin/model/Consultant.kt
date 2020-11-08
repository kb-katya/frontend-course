package model

data class Consultant (
    override var id: Int = 0,
    override var name: String = "",
    override var surname: String = "",
    override var login: String = "Consultant",
    override var password: String = "ConsultantPassword"
) : Employee()
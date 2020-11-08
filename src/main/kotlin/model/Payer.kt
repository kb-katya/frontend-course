package model

data class Payer (
    override var id: Int = 0,
    override var name: String = "",
    override var surname: String = "",
    var address: String = "",
    var income: Int,
    var gender: Boolean,
    var phoneNumber: Int,
    var workExperience: Int,
    var education: String
) : Person()
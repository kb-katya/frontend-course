package model

abstract class Employee (
    override var id: Int = 0,
    override var name: String = "",
    override var surname: String = ""
) : Person() {
    abstract var login: String;
    abstract var password: String;
}
package app.shipcalc

class User(
    FirstName: String,
    LastName: String,
    Email: String,
    Password: String
) {
    // Need to check how to make it constant
    // Fields with getters and setters

    fun setUser(
        fName: String,
        lName: String,
        emailInput: String,
        password: String
    ) : User{
        this.firstName = fName
        this.lastName = lName
        this.email = emailInput
        this.password = password
        return this
    }

    var firstName: String = FirstName
        get() = field
        private set(value) {
            field = value
        }

    var lastName: String = LastName
        get() = field
        private set(value) {
            field = value
        }

    var email: String = Email
        get() = field
        private set(value) {
            field = value
        }

    var password: String = Password
        get() = field
        private set(value) {
            field = value
        }

    var id: String = ""
        get() = field
}
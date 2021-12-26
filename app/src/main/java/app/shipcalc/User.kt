package app.shipcalc

class User(
    FirstName: String,
    LastName: String,
    PhoneNumber: String,
    Password: String
) {
    // Need to check how to make it constant
    // Fields with getters and setters

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

    var phoneNumber: String = PhoneNumber
        get() = field
        private set(value) {
            field = value
        }

    var password: String = Password
        get() = field
        private set(value) {
            field = value
        }
}
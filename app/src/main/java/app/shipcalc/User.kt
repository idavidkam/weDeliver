package app.shipcalc

class User(
    Id: Int,
    FirstName: String,
    LastName: String,
    PhoneNumber: String,
    Password: String
) {
    // Need to check how to make it constant
    // Fields with getters and setters
    private var id: Int = Id
        get() = field
        private set(value) {
            field = value
        }

    private var firstName: String = FirstName
        get() = field
        private set(value) {
            field = value
        }

    private var lastName: String = LastName
        get() = field
        private set(value) {
            field = value
        }

    private var phoneNumber: String = PhoneNumber
        get() = field
        private set(value) {
            field = value
        }

    private var password: String = Password
        get() = field
        private set(value) {
            field = value
        }
}
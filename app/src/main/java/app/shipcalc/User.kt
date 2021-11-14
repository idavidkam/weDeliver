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
    private var id: Int = 0
        get() = field
        private set(value) {
            field = value
        }

    private var firstName: String = ""
        get() = field
        private set(value) {
            field = value
        }

    private var lastName: String = ""
        get() = field
        private set(value) {
            field = value
        }

    private var phoneNumber: String = " "
        get() = field
        private set(value) {
            field = value
        }

    private var password: String = " "
        get() = field
        private set(value) {
            field = value
        }

    // Constructor
    init {
        id = Id
        firstName = FirstName
        lastName = LastName
        phoneNumber = PhoneNumber
        password = Password
    }


}
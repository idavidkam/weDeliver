package app.shipcalc

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class ActivitySignin : AppCompatActivity() {
    lateinit var mySharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()

        var flagIsEmpty: Boolean = false
        var repository: Repository = Repository()

        var firstName = findViewById<TextInputEditText>(R.id.signin_fName)
        var lastName = findViewById<TextInputEditText>(R.id.signin_lName)
        var email = findViewById<TextInputEditText>(R.id.signin_email)
        var password = findViewById<TextInputEditText>(R.id.signin_password)
        var signinButton = findViewById<Button>(R.id.signin_button)

        signinButton.setOnClickListener {
            try {

                // Validate there is not empty fields
                if (firstName.text.toString() == "") {
                    firstName.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if (lastName.text.toString() == "") {
                    lastName.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if (email.text.toString() == "") {
                    email.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if (password.text.toString() == "") {
                    password.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if (!isValidEmail(email.text.toString())) {
                    password.error = "Email should be like a@b.c"
                    flagIsEmpty = true
                }
                if (flagIsEmpty)
                    return@setOnClickListener

                var mySharedPreferences = getSharedPreferences("registeredUsers", MODE_PRIVATE)
                // Validate the account is not existed
                // TODO validate from firebase.authentication the user not existed
                var tempEmail: String =
                    mySharedPreferences.getString(email.text.toString(), "").toString()
                if (tempEmail != "") {
                    Toast.makeText(this, "There is an account for this email already", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
            // registering the user
                var editor: SharedPreferences.Editor = mySharedPreferences.edit()
                editor.putString("LastUser", email.text.toString())
                editor.putString(email.text.toString(), password.text.toString())
                editor.apply()
                repository.addUser(User(firstName.text.toString(),lastName.text.toString(),
                email.text.toString(),password.text.toString()), this)

            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("ERROR")
                    .setMessage("An error occurred in the registration\n\n$e")
                    .setIcon(R.drawable.ic_baseline_error_24)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
            Toast.makeText(this, "The account has created successfully", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this@ActivitySignin,ActivityHome::class.java))
            finish()
        }
    }
    private fun isValidEmail(em : String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(em).matches()
    }
}
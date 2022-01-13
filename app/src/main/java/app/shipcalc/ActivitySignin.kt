package app.shipcalc

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ActivitySignin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()

        // define variables
        var flagIsEmpty: Boolean = false
        var repository: Repository = Repository()
        var mAuto: FirebaseAuth = Firebase.auth

        // create pointers to the fields
        var firstName = findViewById<TextInputEditText>(R.id.signin_fName)
        var lastName = findViewById<TextInputEditText>(R.id.signin_lName)
        var email = findViewById<TextInputEditText>(R.id.signin_email)
        var password = findViewById<TextInputEditText>(R.id.signin_password)
        var signinButton = findViewById<Button>(R.id.signin_button)

        signinButton.setOnClickListener {
            try {
                flagIsEmpty = false

                // Validate there is not empty fields
                if (firstName.text.toString().isBlank()) {
                    firstName.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if (lastName.text.toString().isBlank()) {
                    lastName.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if (email.text.toString().isBlank()) {
                    email.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                // validate the email struct is a valid email
                else if (!isValidEmail(email.text.toString())) {
                    email.error = "Email should be like example@example.example"
                    flagIsEmpty = true
                }
                if (password.text.toString().isBlank()) {
                    password.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                // validate the password is long Enough
                else if (password.text.toString().length < 6) {
                    password.error = "password length can't be small then 6 characters"
                    flagIsEmpty = true
                }

                // return to activity if there is any not correct fields
                if (flagIsEmpty)
                    return@setOnClickListener

                // animationMode
                val inflater = layoutInflater.inflate(R.layout.login_signin_wait, null)
                val processing = AlertDialog.Builder(this).setView(inflater)
                processing.show()

                // define the share preference
                 var mySharedPreferences = getSharedPreferences("registeredUsers", MODE_PRIVATE)
                // registering the user to the firebase.authentication
                mAuto.createUserWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                )
                    .addOnCompleteListener(this) {
                        // validate the user is added to the firebase.authentication
                        if (it.isSuccessful) {
                            // add the user details to the realtime DB
                            repository.addUser(
                                User(
                                    firstName.text.toString(), lastName.text.toString(),
                                    email.text.toString(), password.text.toString()
                                )
                            )

                            // add the new user to share preference
                            var editor: SharedPreferences.Editor = mySharedPreferences.edit()
                            editor.putString("lastUser", email.text.toString())
                            editor.putString(email.text.toString(), password.text.toString())
                            editor.apply()
                            // inform the user that is added successfully
                            Toast.makeText(
                                this,
                                "The account has created successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            // finish the activity and start the ActivityHome
                            startActivity(Intent(this@ActivitySignin, ActivityHome::class.java))
                            finish()
                        }
                    } // inform the user when the account could not be created and do nothing
                    .addOnFailureListener{
                        Toast.makeText(
                            this,
                            "There is an account for this email already",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("ERROR")
                    .setMessage("An error occurred in the registration\n\n$e")
                    .setIcon(R.drawable.ic_baseline_error_24)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }

    // validate the email has correct struct
    private fun isValidEmail(em: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(em).matches()
    }
}
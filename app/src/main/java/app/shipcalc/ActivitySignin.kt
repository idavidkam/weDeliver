package app.shipcalc

import android.app.AlertDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.set
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
        var phoneNumber = findViewById<TextInputEditText>(R.id.signin_phone)
        var password = findViewById<TextInputEditText>(R.id.signin_password)
        var signinButton = findViewById<Button>(R.id.signin_button)

/*

        firstName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().contains('/')){
                    Toast.makeText(this@ActivitySignin, R.string.slashIsNotAllowed,
                        Toast.LENGTH_SHORT).show()
                    var changeView :TextView = firstName as TextView
                    changeView.text = s.toString().dropLast(1)
                    changeView.setText(R.string.app_name)
                    //changeView.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().contains('/')) {

                }
            }
        })
*/

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
                if (phoneNumber.text.toString() == "") {
                    phoneNumber.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if (password.text.toString() == "") {
                    password.error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if (flagIsEmpty)
                    return@setOnClickListener

                // TODO: validate the phone doesn't registered yet
                // todo: add user details to share preference + firebase (via the repository)

                var mySharedPreferences = getSharedPreferences("registeredUsers", MODE_PRIVATE)
                // Validate the account is not existed
                var tempPhone: String =
                    mySharedPreferences.getString(phoneNumber.text.toString(), "").toString()
                if (tempPhone != "") {
                    Toast.makeText(this, "${tempPhone} has an account already", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
            // registering the user
                var editor: SharedPreferences.Editor = mySharedPreferences.edit()
                editor.putString(phoneNumber.text.toString(), password.text.toString())
                repository.addUser(User(firstName.text.toString(),lastName.text.toString(),
                phoneNumber.text.toString(),password.text.toString()))
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("ERROR")
                    .setMessage("We apologize but an error occurred while trying to send the information\n\n" + e.toString())
                    .setIcon(R.drawable.ic_baseline_error_24)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }


        }
    }
}
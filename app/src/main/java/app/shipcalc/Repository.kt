package app.shipcalc

import android.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.Exception

public class Repository {


    // fire base
    var databade = FirebaseDatabase.getInstance()
    var myRef = databade.getReference()

    fun addUser(user: User) {
        var addRef = myRef.child("NewUser").child("User_${user.phoneNumber}")
        addRef.child("userFirstName").setValue(user.firstName.toString())
        addRef.child("userLastName").setValue(user.lastName.toString())
        addRef.child("userPhoneNumber").setValue(user.phoneNumber.toString())
        addRef.child("userPassword").setValue(user.password.toString())
    }

    fun getUser(phoneNumber: String): User {

        var fName: String = ""
        var lName: String = ""
        var password: String = ""
        var success: Boolean = true
        val refUser = FirebaseDatabase.getInstance().getReference("NewUser")
        refUser.child("User_${phoneNumber}").child("userFirstName").get().addOnSuccessListener {
            fName = (it.value.toString())
        }.addOnFailureListener {
            success = false
        }
        refUser.child("User_${phoneNumber}").child("userLastName").get().addOnSuccessListener {
            lName = (it.value.toString())
        }.addOnFailureListener {
            success = false
        }
        refUser.child("User_${phoneNumber}").child("userPassword").get().addOnSuccessListener {
            password = (it.value.toString())
        }.addOnFailureListener {
            success = false
        }
        if (!success) {
            throw Exception("communication problem")
        } else {
            return User(fName, lName, phoneNumber, password)
        }
    }


}
package app.shipcalc

import android.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        var phoneNumber: String = ""
        var password: String = ""
        lateinit var user12: User

        val myRefUsers = myRef.child("NewUser").child("User_${phoneNumber}")
        myRefUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        var user: User? = userSnapshot.getValue(User::class.java)
                        if (user?.phoneNumber == phoneNumber) {
                            fName = user?.firstName
                            lName = user?.lastName.toString()
                            phoneNumber = user?.phoneNumber.toString()
                            password = user?.password.toString()
                        }
                        user12 = User(fName, lName, phoneNumber, password)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw Exception("Connection problem")
            }

        })
        return user12
    }


}
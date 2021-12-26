package app.shipcalc

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

public class Repository {

    var userCounter: Int = 0
    // fire base
    var databade = FirebaseDatabase.getInstance()
    var myRef = databade.getReference()

    fun addUser(user : User){
        var addRef = myRef.child("NewUser").child("User_${++userCounter}")
        //addRef.child("userName").setValue(user.firstName.toString())


    }


}
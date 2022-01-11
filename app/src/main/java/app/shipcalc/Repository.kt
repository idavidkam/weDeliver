package app.shipcalc

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class Repository {

    private var mAuto: FirebaseAuth = Firebase.auth
    private var dataBase = FirebaseDatabase.getInstance()
    private val myRefPackages = dataBase.getReference("packages")
    private val myRefUsers = dataBase.getReference("usersDetails")

    fun addUser(user: User) {
        user.id = myRefUsers.push().key.toString()
        myRefUsers.child(user.id).setValue(user)
    }

    fun getPackages() {
        var packagesList = mutableListOf<Package>()
        val childEventListener = object : ChildEventListener{


        }
    }


}
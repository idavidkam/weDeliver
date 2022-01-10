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

    fun getPackages(list: Array<Package>) {

    }

    fun addUser(user: User) {
        user.id = myRefUsers.push().key.toString()
        myRefUsers.child(user.id).setValue(user)
    }


    fun logInFirebase(email: String, password: String, activityLogin: ActivityLogin) {
        mAuto.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activityLogin) {
                if (!it.isComplete)
                    throw Exception("log in Fails")
            }
    }

    fun getLastUserFromFirebase(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun getUser(email: String, password: String): User {
       /* lateinit var returnUser: User

        myRefUsers.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children){
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
                        if(user.email == email && user.password == password){
                            returnUser = user
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        try{
            if (returnUser.email == "")
                returnUser.id = ""
        }
        catch (e: Exception){
            throw Exception("No user found")
        }

        return returnUser*/
        return User("ytt","cdwvjf",email,password)
    }
}
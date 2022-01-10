package app.shipcalc

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class Repository {

    private var mAuto: FirebaseAuth = Firebase.auth
    private var dataBase = FirebaseDatabase.getInstance()
    private val myRefPackages = dataBase.getReference("packages")
    private val myRefUsers = dataBase.getReference("usersDetails")

    fun getPackages(list: Array<Package>) {

    }

    fun addUser(user: User, activitySignin: ActivitySignin) {
        mAuto.createUserWithEmailAndPassword(user.email, user.password)
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

    fun getLastUserFromFirebase(): User {
        throw Exception("")
    }

    fun getUser(email: String, password: String): User {
        return User("TEST", "TEST", email, password)
    }
}
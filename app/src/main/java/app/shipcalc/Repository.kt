package app.shipcalc

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

public class Repository {

    private lateinit var mAuto: FirebaseAuth
    private var dataBase = FirebaseDatabase.getInstance()
    private val myRefPackages = dataBase.getReference("packages")
    private val myRefUsers = dataBase.getReference("usersDetails")

    init {
        mAuto = FirebaseAuth.getInstance()
    }

    fun getPackages(list: Array<Package>) {

    }

    fun addUser(user: User, activitySignin: ActivitySignin) {
        mAuto.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(activitySignin, OnCompleteListener<AuthResult> { task ->
                when {
                    task.isSuccessful -> {
                        user.id = myRefUsers.push().key.toString()
                        myRefUsers.child(user.id).setValue(user)
                        return@OnCompleteListener
                    }
                }
            }).addOnFailureListener(activitySignin, OnFailureListener {
            throw Exception("Failed to register email ${user.email}")
        })


    }

    fun getUser(userID: String): User {


        throw Exception("")
    }
}
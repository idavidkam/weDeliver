package app.shipcalc

import android.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.lang.Exception

public class Repository {

    private lateinit var mAuto: FirebaseAuth
    private var dataBase = FirebaseDatabase.getInstance()
    private val refMyPakcages = dataBase.getReference("packages")

    init {
        mAuto = FirebaseAuth.getInstance()
    }

    fun getPackages(list: Array<Package>)
    {

    }

    fun addUser(user : User, activityLogin: ActivitySignin){
        mAuto.createUserWithEmailAndPassword(user.email,user.password).
        addOnCompleteListener(activityLogin, OnCompleteListener<AuthResult>{ task ->
            when {
                task.isSuccessful -> {
                   return@OnCompleteListener
                }
            }
        } ).addOnFailureListener(activityLogin, OnFailureListener {
           throw Exception("Failed to register email ${user.email}")
        })



    }

    fun getUser(userID : String):User{

        throw Exception("")
    }
}
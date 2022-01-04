package app.shipcalc

import android.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.lang.Exception

public class Repository {

private lateinit var mAuto : FirebaseAuth

init {
    mAuto = FirebaseAuth.getInstance()
}

    //TODO להעביר את הפיירבייס הזה לאקטיביטי סיינאין כי זה צריך לעבור בין אקטיביטי שונים
    /*fun addUser(user : User){
        mAuto.createUserWithEmailAndPassword(user.email,user.password).
        addOnCompleteListener(this, OnCompleteListener<AuthResult>() {

        })


    }*/

}
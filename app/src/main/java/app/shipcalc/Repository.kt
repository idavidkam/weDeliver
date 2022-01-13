package app.shipcalc

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class Repository {

    private var mAuto: FirebaseAuth = Firebase.auth
    private var dataBase = FirebaseDatabase.getInstance()
    private val myRefPackages = dataBase.getReference("packages")
    private val myRefUsers = dataBase.getReference("usersDetails")
    var packagesList = mutableListOf<Package>()

    fun addUser(user: User) {
        user.id = myRefUsers.push().key.toString()
        myRefUsers.child(user.id).setValue(user)
    }

    fun updatePackage(updatePackage: Package) {

        myRefPackages.child(updatePackage.id.toString()).removeValue()
        updatePackage.id = myRefPackages.push().key.toString()
        myRefPackages.child(updatePackage.id!!).setValue(updatePackage)
    }
}



package app.shipcalc

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
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

    /*fun getPackages() : ArrayList<Package>{
       *//* var tempPackagesList = mutableListOf<Package>()
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val myPackageValue = snapshot.getValue<Package>()
                val myPackageKey = snapshot.key

                if (myPackageValue != null && myPackageKey != null) {
                    //if (myPackageValue.status == PackageStatus.TOPICKUP)
                    tempPackagesList.add(myPackageValue)
                }
                packagesList.postValue(tempPackagesList)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        }*//*
    }*/


}
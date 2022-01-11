package app.shipcalc

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class ActivityHome : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private val repository: Repository = Repository()
    private var dataBase = FirebaseDatabase.getInstance()
    private val myRefUsers = dataBase.getReference("usersDetails")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //TODO("validate the drawer is in left side in the phone")
        val toolbar: Toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        // find the Header view in order to change username in the nav_drawer
        val headerNav: View = navigationView.getHeaderView(0)
        val textHeaderNav: TextView = headerNav.findViewById(R.id.nav_helloUser)

        try {
            // get user details
            myRefUsers.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            try {
                                val user: User? = userSnapshot.getValue(User::class.java)
                                if (user != null) {
                                    if (user.email == FirebaseAuth.getInstance().currentUser?.email?.toString()) {

                                        // set the header
                                        textHeaderNav.setText("Hello ${user.firstName} ${user.lastName}")

                                        Toast.makeText(
                                            applicationContext,
                                            "success in header set",
                                            Toast.LENGTH_SHORT
                                        )
                                        return
                                    }
                                }
                            }
                            catch (e: Exception){
                                Toast.makeText(
                                    applicationContext,
                                    "failed in header set${e}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    throw Exception("get user details from firebase error")
                }

            })
        } catch (e: Exception) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("ERROR")
                .setMessage("Error occurred in header set\n\n${e}")
                .setIcon(R.drawable.ic_baseline_error_24)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_pickAPackage -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragmentContainer,
                    FragmentPickPackage()
                ).commit()
            }
            R.id.nav_waitingPackaged -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragmentContainer,
                    FragmentWaitingPackages()
                ).commit()
            }
            R.id.nav_history -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragmentContainer,
                    FragmentHistoryPackages()
                ).commit()
            }
            R.id.nav_logout -> {
                //TODO Clear shearedPreferences and logout from the firebase
                startActivity(Intent(this, ActivityLogin::class.java))
            }
            else -> {
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
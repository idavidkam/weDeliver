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
import java.lang.Exception

class ActivityHome : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    val repository: Repository = Repository()
    lateinit var user: User

    // TEST12
    lateinit var listViewPickPackage: ListView
    var listPackages = arrayListOf<Package>()


    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //TODO("validate the drawer is in left side in the phone")
        var toolbar: Toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        var navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        var toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        try {
            // get the user phone
            var currentUserPhone: String = intent.getStringExtra("currentUser").toString()
            // get the user by his phone
            //TODO - get the User if eh registered
            //user = repository.getUser(currentUserPhone)

            // find the Header view in order to change username in the nav_drawer
            var headerNav: View = navigationView.getHeaderView(0)
            var textHeaderNav: TextView = headerNav.findViewById(R.id.nav_helloUser)
            textHeaderNav.setText("Hello ${user.firstName} ${user.lastName}")
        } catch (e: Exception) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("ERROR")
                .setMessage("Error occurred in header set\n\n${e}")
                .setIcon(R.drawable.ic_baseline_error_24)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        //TEST12
        listViewPickPackage = findViewById(R.id.ListViewPickPackage)
        var arrayAdapter : ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_list_item_1,listPackages )
        listViewPickPackage.setAdapter(arrayAdapter)

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
                //TODO Clear sheredPrefences and logout from the firebase
                startActivity(Intent(this, ActivityLogin::class.java))
            }
            else -> {
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
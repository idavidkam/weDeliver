package app.shipcalc

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.internal.ToolbarUtils
import com.google.android.material.navigation.NavigationView
import java.lang.Exception

class ActivityHome : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    lateinit var addPackageButton: Button
    val repository: Repository = Repository()
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // get the user phone
        var currentUserPhone: String = intent.getStringExtra("currentUser").toString()
        // get the user by his phone
        user = repository.getUser(currentUserPhone)

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


        /*addPackageButton = findViewById(R.id.home_button_addPackage)
        addPackageButton.setOnClickListener {
            startActivity(Intent(this@ActivityHome, ActivityAddPackage::class.java))

        }*/
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
            R.id.nav_addPackage -> {
                startActivity(Intent(this, ActivityAddPackage::class.java))
            }
            R.id.nav_editPackage -> {
                Toast.makeText(this, "coming soon...", Toast.LENGTH_LONG)
            }
            else -> {
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
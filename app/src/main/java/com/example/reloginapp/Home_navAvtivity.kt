package com.example.reloginapp

import Extensions.startActivity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Home_navAvtivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_nav_avtivity)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_Logout -> {

                    val mAlertDialog = AlertDialog.Builder(this)
                    mAlertDialog.setTitle("Alert")
                    mAlertDialog.setMessage("Are you sure you want to logout")
                    mAlertDialog.setPositiveButton("YES"){mAlertDialog, id->

                        val sharedPreferences = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        editor.apply(){

                            putString("SignEmail",null)
                            putString("SignPassword",null)

                        }.apply()
                        Toast.makeText(applicationContext,"You are logout",Toast.LENGTH_LONG).show()

                        startActivity<LoginActivity>()

                    }
                    mAlertDialog.setNegativeButton("NO"){mAlertDialog,id->
                       mAlertDialog.dismiss()
                    }
                    mAlertDialog.show()
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_nav_avtivity, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
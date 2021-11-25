package com.example.triple_kill_7_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    //dumpsys activity com.example.lab_three_point_three | grep Fragment{
    private lateinit var navController: NavController
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = (supportFragmentManager.findFragmentById(R.id.fragContainer)
                as NavHostFragment).navController
        drawer = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawer)

        setupActionBarWithNavController(navController,
            AppBarConfiguration(navController.graph, drawer))

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

        toggle = ActionBarDrawerToggle(this, drawer,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onSupportNavigateUp(): Boolean {
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        return if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
            true
        } else {
            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }
}
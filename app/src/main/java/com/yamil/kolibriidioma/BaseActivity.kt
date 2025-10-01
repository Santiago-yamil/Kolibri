package com.yamil.kolibriidioma

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

abstract class BaseActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    protected lateinit var drawerLayout: DrawerLayout
    protected lateinit var navView: NavigationView
    protected lateinit var toolbar: Toolbar

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.activity_barra, null) as DrawerLayout
        val activityContainer: FrameLayout = fullView.findViewById(R.id.content_frame)
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_animales -> startActivity(Intent(this, MainActivity::class.java))
            R.id.nav_colores -> startActivity(Intent(this, SegundoActivity::class.java))
            R.id.nav_frutas -> startActivity(Intent(this, TercerActivity::class.java))
            R.id.nav_about -> {
                // Aquí puedes abrir una pantalla de "Acerca de"
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

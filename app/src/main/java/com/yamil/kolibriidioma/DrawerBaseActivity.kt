package com.yamil.kolibriidioma

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

abstract class DrawerBaseActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    protected lateinit var drawerLayout: DrawerLayout
    protected lateinit var navView: NavigationView
    protected lateinit var toolbar: Toolbar

    // Cada Activity hija debe decir cuál item es “el suyo”
    abstract fun currentDestId(): Int

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.activity_barra, null) as DrawerLayout
        val activityContainer: FrameLayout = fullView.findViewById(R.id.content_frame)
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        drawerLayout = fullView.findViewById(R.id.drawer_layout)
        navView = fullView.findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        // Marcar seleccionado el item de la pantalla actual
        navView.setCheckedItem(currentDestId())
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)

        val target = when (item.itemId) {
            R.id.nav_pagPrin -> MainActivityDrawer::class.java
            R.id.nav_dicc    -> SegundoActivityDrawer::class.java
            R.id.nav_idioma  -> Idioma::class.java
            R.id.nav_tianguis-> com.yamil.kolibriidioma.recursos_cuatro.ComprasActivityDrawer::class.java
            R.id.nav_about   -> acercaNosotros::class.java
            else             -> null
        } ?: return true

        // 1) Si ya estoy en esa Activity, no hagas nada
        if (this::class.java == target) return true

        // 2) Navegar sin duplicar instancias
        val intent = Intent(this, target).apply {
            // Reusa si ya existe en el stack y súbela al frente
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            // Y si hay otra instancia antes en la pila, limpia duplicados
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)

        // Opcional: si no quieres volver con back a la anterior, termina la actual
        // finish()

        return true
    }
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManager.wrap(newBase))
    }


}

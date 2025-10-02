package com.yamil.kolibriidioma

import android.os.Bundle

class acercaNosotros : DrawerBaseActivity() {

    override fun currentDestId(): Int = R.id.nav_about

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usa setContentView con tu layout normal
        setContentView(R.layout.acerca_de)
    }
}

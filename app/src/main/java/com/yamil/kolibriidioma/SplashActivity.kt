package com.yamil.kolibriidioma
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoPrincipal = findViewById<ImageView>(R.id.logoPrincipal)
        val logoSecundario = findViewById<ImageView>(R.id.logoSecundario)

        // 1) calcular cuanto hay que mover para que queden fuera de la pantalla
        val screenH = resources.displayMetrics.heightPixels.toFloat()

        // 2) ubicar fuera de pantalla (sin tocar translationX, la dejamos como está en XML)
        logoPrincipal.translationY = -screenH    // comienza arriba, fuera de la pantalla
        logoSecundario.translationY = screenH    // comienza abajo, fuera de la pantalla

        // 3) animar ambos a la posición final (translationY = 0)
        val duration = 900L
        val interpolator = AccelerateDecelerateInterpolator()

        logoPrincipal.animate()
            .translationY(0f)
            .setDuration(duration)
            .setInterpolator(interpolator)
            .start()

        logoSecundario.animate()
            .translationY(0f)
            .setDuration(duration)
            .setInterpolator(interpolator)
            .start()


        // 4) después de X ms arrancar MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            val selectedOption = sharedPref.getString("selected_option", null)

            if (selectedOption == null) {
                // Primera vez → mostrar pantalla de selección de idioma jjjj
                startActivity(Intent(this, SelectionLanguageActivity::class.java))
            } else {
                // Ya hay idioma guardado → ir directo a la pantalla principal
                startActivity(Intent(this, DetailProductActivity::class.java))
            }

            finish() // cerrar esta actividad
        }, 3000)
    }




}

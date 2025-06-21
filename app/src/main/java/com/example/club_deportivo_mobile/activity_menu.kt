package com.example.club_deportivo_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_menu : AppCompatActivity() {

    private lateinit var btnRegistrarActividad: Button
    private lateinit var btnCobrarActividad: Button
    private lateinit var imgVolver: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_activity_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRegistrarActividad = findViewById(R.id.btn_registrar_actividad)
        btnCobrarActividad = findViewById(R.id.btn_cobrar_actividad)
        imgVolver = findViewById(R.id.img_volver_activity)

        imgVolver.setOnClickListener {
            startActivity(Intent(this, menuPrincipal::class.java))
            finish()
        }

        btnRegistrarActividad.setOnClickListener {
            startActivity(Intent(this, registrar_actividad::class.java))
        }

        btnCobrarActividad.setOnClickListener {
            startActivity(Intent(this, cobrar_actividad::class.java))
        }
    }
}
package com.example.club_deportivo_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class cobrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cobrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var btnCobrar = findViewById<Button>(R.id.tv_btnCobrar)
        var btnBuscar = findViewById<Button>(R.id.tv_btnBuscar)
        var volver = findViewById<ImageView>(R.id.img_volver_buscar2)

        btnBuscar.setOnClickListener {
            val intent = Intent(this, cobrar2::class.java)
            startActivity(intent)
        }

        volver.setOnClickListener {
            val intent = Intent(this, menuPrincipal::class.java)
            startActivity(intent)
        }
    }
}
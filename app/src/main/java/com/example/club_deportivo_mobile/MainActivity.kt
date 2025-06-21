package com.example.club_deportivo_mobile

import DataBaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        this.deleteDatabase("clubDeportivo.db")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Crea la BD si no existe
        val dbHelper = DataBaseHelper(this)
        val db = dbHelper.writableDatabase

        val iniciarSesion = findViewById<Button>(R.id.btn_iniciarSesion)
        val registrarse = findViewById<Button>(R.id.btnRegistrarse)

        iniciarSesion.setOnClickListener {
            var intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        registrarse.setOnClickListener {
            var intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

    }
}
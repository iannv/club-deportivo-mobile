package com.example.club_deportivo_mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menuPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegistrar = findViewById<Button>(R.id.btn_registrar_cliente)
        val btnBuscar = findViewById<Button>(R.id.btn_buscar_cliente)
        val btnActividades = findViewById<Button>(R.id.btn_actividades_socio)
        val btnCobrar = findViewById<Button>(R.id.btn_cobrar)
        val btnVtos = findViewById<Button>(R.id.btn_vtos)


        btnRegistrar.setOnClickListener{
            var intent = Intent(this, registrar_cliente::class.java)
            startActivity(intent)
        }

        btnBuscar.setOnClickListener {
            var intent = Intent(this, buscar_cliente::class.java)
            startActivity(intent)
        }

        btnActividades.setOnClickListener {
            var intent = Intent(this, actividades_socio::class.java)
            startActivity(intent)
        }

        btnCobrar.setOnClickListener {
            var intent = Intent(this, cobrar::class.java)
            startActivity(intent)
        }

        btnVtos.setOnClickListener {
            var intent = Intent(this, lista_vencimientos::class.java)
            startActivity(intent)
        }


    }
}
package com.example.club_deportivo_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menuPrincipal : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
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
        val btnActivity = findViewById<Button>(R.id.btn_activity) // Nuevo botón
        val cerrarSesion = findViewById<ImageView>(R.id.img_cerrar_sesion)
        val salir = findViewById<TextView>(R.id.tv_salir)

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

        // Nuevo listener para Activity
        btnActivity.setOnClickListener {
            var intent = Intent(this, activity_menu::class.java)
            startActivity(intent)
        }

        cerrarSesion.setOnClickListener {
            var intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        salir.setOnClickListener {
            var intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}
package com.example.club_deportivo_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class buscar_cliente : AppCompatActivity() {

    private lateinit var btnBuscar: Button
    private lateinit var etDni: EditText
    private lateinit var imgVolver: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_cliente)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnBuscar = findViewById(R.id.btn_buscar)
        etDni = findViewById(R.id.et_dni_buscar)
        imgVolver = findViewById(R.id.img_volver_buscar)

        imgVolver.setOnClickListener {
            startActivity(Intent(this, menuPrincipal::class.java))
            finish()
        }

        btnBuscar.setOnClickListener {
            val dni = etDni.text.toString()
            if (dni.isNotEmpty()) {
                buscarCliente(dni)
            } else {
                Toast.makeText(this, "Por favor, ingrese un DNI", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, datos_socio::class.java)
            startActivity(intent)
        }
    }

    private fun buscarCliente(dni: String) {
        Toast.makeText(this, "Buscando cliente con DNI: $dni", Toast.LENGTH_SHORT).show()

    }
}
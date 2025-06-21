package com.example.club_deportivo_mobile

import Actividad
import DataBaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class registrar_actividad : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etPrecio: EditText
    private lateinit var btnGuardar: Button
    private lateinit var imgVolver: ImageView
    private lateinit var tvMensaje: TextView
    private lateinit var dbHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_activity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNombre = findViewById(R.id.et_nombre_actividad)
        etPrecio = findViewById(R.id.et_precio_actividad)
        btnGuardar = findViewById(R.id.btn_guardar_actividad)
        imgVolver = findViewById(R.id.img_volver_registrar_actividad)
        tvMensaje = findViewById(R.id.tv_mensaje_actividad)
        
        dbHelper = DataBaseHelper(this)

        imgVolver.setOnClickListener {
            startActivity(Intent(this, activity_menu::class.java))
            finish()
        }

        btnGuardar.setOnClickListener {
            registrarActividad()
        }
    }

    private fun registrarActividad() {
        val nombre = etNombre.text.toString().trim()
        val precioStr = etPrecio.text.toString().trim()

        if (nombre.isEmpty() || precioStr.isEmpty()) {
            tvMensaje.text = "Todos los campos son obligatorios"
            return
        }

        try {
            val precio = precioStr.toFloat()

            if (precio < 0) {
                tvMensaje.text = "El precio no puede ser negativo"
                return
            }

            val actividad = Actividad(
                nombre = nombre,
                precio = precio
            )

            dbHelper.registrarActividad(actividad, this)
            Toast.makeText(this, "Actividad registrada exitosamente", Toast.LENGTH_SHORT).show()
            
            // Limpiar campos
            etNombre.text.clear()
            etPrecio.text.clear()
            tvMensaje.text = ""

        } catch (e: NumberFormatException) {
            tvMensaje.text = "Formato de número inválido"
        }
    }
}
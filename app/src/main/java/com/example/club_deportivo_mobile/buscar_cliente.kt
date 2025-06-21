package com.example.club_deportivo_mobile

import DataBaseHelper
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
    private lateinit var databaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_cliente)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseHelper = DataBaseHelper(this)

        btnBuscar = findViewById(R.id.btn_buscar)
        etDni = findViewById(R.id.et_dni_buscar)
        imgVolver = findViewById(R.id.img_volver_buscar)

        imgVolver.setOnClickListener {
            startActivity(Intent(this, menuPrincipal::class.java))
            finish()
        }

        btnBuscar.setOnClickListener {
            val dni = etDni.text.toString().trim()
            if (dni.isNotEmpty()) {
                buscarCliente(dni)
            } else {
                Toast.makeText(this, "Por favor, ingrese un DNI", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buscarCliente(dni: String) {
        val cliente = databaseHelper.buscarClientePorDni(dni)

        if (cliente != null) {
            val intent = Intent(this, datos_socio::class.java)
            intent.putExtra("DNI", cliente.dni)
            intent.putExtra("NOMBRE", cliente.nombre)
            intent.putExtra("APELLIDO", cliente.apellido)
            intent.putExtra("TELEFONO", cliente.telefono)
            intent.putExtra("FECHA_ALTA", cliente.fechaAlta)
            intent.putExtra("NUMERO_CARNET", cliente.numeroCarnet)
            intent.putExtra("ID_CLIENTE", cliente.id_cliente)
            startActivity(intent)
        } else {
            Toast.makeText(this, "No se encontró ningún cliente con el DNI: $dni", Toast.LENGTH_LONG).show()
        }
    }
}

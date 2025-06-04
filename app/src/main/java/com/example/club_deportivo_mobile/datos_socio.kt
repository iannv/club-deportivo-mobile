package com.example.club_deportivo_mobile

import DataBaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class datos_socio : AppCompatActivity() {

    private lateinit var imgVolver: ImageView
    private lateinit var tvApellidoNombre: TextView
    private lateinit var tvDni: TextView
    private lateinit var tvSocioNro: TextView
    private lateinit var tvTelefono: TextView
    private lateinit var tvAlta: TextView
    private lateinit var tvCuota: TextView
    private lateinit var databaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_socio)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar base de datos
        databaseHelper = DataBaseHelper(this)

        // Inicializar vistas
        imgVolver = findViewById(R.id.img_volver_datos)
        tvApellidoNombre = findViewById(R.id.tv_apellido_nombre_valor)
        tvDni = findViewById(R.id.tv_dni_valor)
        tvSocioNro = findViewById(R.id.tv_socio_nro_valor)
        tvTelefono = findViewById(R.id.tv_telefono_valor)
        tvAlta = findViewById(R.id.tv_alta_valor)
        tvCuota = findViewById(R.id.tv_cuota_valor)

        // Configurar botón de volver
        imgVolver.setOnClickListener {
            startActivity(Intent(this, buscar_cliente::class.java))
            finish()
        }

        // Cargar datos del cliente desde el intent
        cargarDatosDesdeIntent()
    }

    private fun cargarDatosDesdeIntent() {
        val dni = intent.getStringExtra("DNI") ?: ""
        val nombre = intent.getStringExtra("NOMBRE") ?: ""
        val apellido = intent.getStringExtra("APELLIDO") ?: ""
        val telefono = intent.getStringExtra("TELEFONO") ?: ""
        val fechaAlta = intent.getStringExtra("FECHA_ALTA") ?: ""
        val numeroCarnet = intent.getIntExtra("NUMERO_CARNET", 0)
        val idCliente = intent.getIntExtra("ID_CLIENTE", 0)

        // Obtener la cuota más reciente
        val cuotaMonto = databaseHelper.obtenerUltimaCuota(idCliente)

        // Actualizar la UI con los datos
        tvApellidoNombre.text = "$apellido, $nombre"
        tvDni.text = dni
        tvSocioNro.text = numeroCarnet.toString()
        tvTelefono.text = telefono
        tvAlta.text = fechaAlta ?: "Sin fecha"
        tvCuota.text = cuotaMonto?.toString() ?: "Sin cuota registrada"
    }
}



//package com.example.club_deportivo_mobile
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class datos_socio : AppCompatActivity() {
//
//    private lateinit var imgVolver: ImageView
//    private lateinit var tvApellidoNombre: TextView
//    private lateinit var tvDni: TextView
//    private lateinit var tvSocioNro: TextView
//    private lateinit var tvTelefono: TextView
//    private lateinit var tvAlta: TextView
//    private lateinit var tvCuota: TextView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_datos_socio)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        // Inicializar vistas
//        imgVolver = findViewById(R.id.img_volver_datos)
//        tvApellidoNombre = findViewById(R.id.tv_apellido_nombre_valor)
//        tvDni = findViewById(R.id.tv_dni_valor)
//        tvSocioNro = findViewById(R.id.tv_socio_nro_valor)
//        tvTelefono = findViewById(R.id.tv_telefono_valor)
//        tvAlta = findViewById(R.id.tv_alta_valor)
//        tvCuota = findViewById(R.id.tv_cuota_valor)
//
//        // Configurar botón de volver
//        imgVolver.setOnClickListener {
//            // Asumiendo que venimos de la pantalla de buscar cliente
//            startActivity(Intent(this, buscar_cliente::class.java))
//            finish()
//        }
//
//        // Obtener el DNI del intent (si se pasa desde la pantalla de búsqueda)
//        val dni = intent.getStringExtra("DNI")
//
//        // Si tenemos el DNI, cargar los datos del socio
//        dni?.let {
//            cargarDatosSocio(it)
//        }
//    }
//
//    private fun cargarDatosSocio(dni: String) {
//        // En el futuro, aquí se implementaría la consulta a la base de datos
//        // Por ahora, usamos datos de ejemplo hardcodeados
//
//        // Datos de ejemplo (en producción, estos vendrían de la BD)
//        val apellidoNombre = "RODRIGUEZ, KEVIN"
//        val socioNro = "500"
//        val telefono = "1122334455"
//        val alta = "12/03/2025"
//        val cuota = "5000"
//
//        // Actualizar la UI con los datos
//        tvApellidoNombre.text = apellidoNombre
//        tvDni.text = dni
//        tvSocioNro.text = socioNro
//        tvTelefono.text = telefono
//        tvAlta.text = alta
//        tvCuota.text = cuota
//    }
//}



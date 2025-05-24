package com.example.club_deportivo_mobile

import DataBaseHelper
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

class carnet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carnet)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var volver = findViewById<ImageView>(R.id.img_volver2)
        var nombre = findViewById<TextView>(R.id.tv_nombreA)
        var apellido = findViewById<TextView>(R.id.tv_apellidoA)
        var dni = findViewById<TextView>(R.id.tv_dniA)
        var fechaAlta = findViewById<TextView>(R.id.tv_fecha_altaA)
        var numeroCarnet = findViewById<TextView>(R.id.tv_numeroA)
        var socio = findViewById<TextView>(R.id.tv_socio_nosocio)

        val dbHelper = DataBaseHelper(this)
        val bd = dbHelper.readableDatabase

        val dniRecibido = intent.getStringExtra("dni")
        if (dniRecibido != null) {
            val cursor = bd.rawQuery("SELECT nombre, apellido, dni, fechaAlta, numeroCarnet, socio FROM cliente WHERE dni = ?", arrayOf(dniRecibido))

            if (cursor.moveToFirst()) {
                nombre.text = cursor.getString(0).replaceFirstChar { it.uppercase() }
                apellido.text = cursor.getString(1).replaceFirstChar { it.uppercase() }
                dni.text = cursor.getString(2)
                fechaAlta.text = cursor.getString(3)
                numeroCarnet.text = cursor.getInt(4).toString()

                val esSocio = cursor.getInt(5) == 1
                socio.text = if (esSocio) "SOCIO" else "NO SOCIO"
            }
            cursor.close()
        }
        bd.close()


        volver.setOnClickListener {
            startActivity(Intent(this, menuPrincipal::class.java))
        }
    }
}
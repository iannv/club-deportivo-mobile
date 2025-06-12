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
import androidx.gridlayout.widget.GridLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class lista_vencimientos : AppCompatActivity() {

    private lateinit var imgRetorno: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_vencimientos)
        var hoy: TextView
        hoy = findViewById(R.id.tv_Fecha)
        val calendar = Calendar.getInstance()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaAlta = formatoFecha.format(calendar.time)
        hoy.text = fechaAlta

        val gridLayoutResultado: GridLayout = findViewById(R.id.grid)
        val db = DataBaseHelper(this)
        var clientes = db.listadoDeVencimientos(fechaAlta)

        for(cliente in clientes){
            val textViewPropiedad1 = TextView(this).apply {
                text = cliente.id_cliente.toString()
            }
            val textViewPropiedad2 = TextView( this).apply {
                text = cliente.nombre
            }
            val textViewPropiedad3 = TextView(this).apply {
                text = cliente.apellido
            }
            val textViewPropiedad4 = TextView( this).apply {
                text = cliente.numeroCarnet.toString()
            }

            val params = GridLayout.LayoutParams()
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,  1f)


            textViewPropiedad1.layoutParams = params
            textViewPropiedad2.layoutParams = params
            textViewPropiedad3.layoutParams = params
            textViewPropiedad4.layoutParams = params
            gridLayoutResultado.addView(textViewPropiedad1)
            gridLayoutResultado.addView(textViewPropiedad2)
            gridLayoutResultado.addView(textViewPropiedad3)
            gridLayoutResultado.addView(textViewPropiedad4)


        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgRetorno = findViewById(R.id.img_volver_buscar)

        imgRetorno.setOnClickListener {
            startActivity(Intent(this, menuPrincipal::class.java))
            finish()
        }
    }



}

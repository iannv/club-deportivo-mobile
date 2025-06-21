package com.example.club_deportivo_mobile

import DataBaseHelper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.print.PrintHelper

class cobrar3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cobrar3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var db = DataBaseHelper(this)
        var volver = findViewById<Button>(R.id.tv_btnVolver)
        var imgVolver = findViewById<ImageView>(R.id.img_volver_buscar3)
        val intent = intent
        val dni = intent.getStringExtra("dni")
        var label6 = findViewById<TextView>(R.id.labela6)
        label6.visibility = TextView.INVISIBLE

        val clienteEncontrado = db.buscarClientePorDni(dni.toString())
        var layout = findViewById<View>(R.id.main)

        if(clienteEncontrado != null){
            if (clienteEncontrado.socio){
                label6.visibility = TextView.VISIBLE
            }
        }

        var numeroSocio = findViewById<TextView>(R.id.numeroSocioC3)
        var nombre = findViewById<TextView>(R.id.nombreC3)
        var apellido = findViewById<TextView>(R.id.apellidoC3)
        var fecha = findViewById<TextView>(R.id.fechaC3)
        var monto = findViewById<TextView>(R.id.montoC3)



        numeroSocio.text = clienteEncontrado?.numeroCarnet.toString()
        nombre.text = clienteEncontrado?.nombre.toString()
        apellido.text = clienteEncontrado?.apellido.toString()
        fecha.text = intent.getStringExtra("fecha")
        monto.text = intent.getStringExtra("monto")


        volver.setOnClickListener {
            val intent = Intent(this, menuPrincipal::class.java)
            startActivity(intent)
        }

        imgVolver.setOnClickListener {
            val intent = Intent(this, cobrar2::class.java)
            startActivity(intent)
        }

        var imprimirBtn = findViewById<Button>(R.id.tv_btnImprimir)

        imprimirBtn.setOnClickListener{
            layout.post {
                imprimirComprobante(layout)
            }
        }

    }


    private fun capturarVistaComoBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun imprimirComprobante(view: View) {
        val bitmap = capturarVistaComoBitmap(view)
        val printHelper = PrintHelper(this)
        printHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
        printHelper.printBitmap("Cuota comprobante", bitmap)
    }
}
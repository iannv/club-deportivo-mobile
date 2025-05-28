package com.example.club_deportivo_mobile

import DataBaseHelper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextPaint
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.print.PrintHelper
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import java.util.jar.Manifest

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
        var btnImprimir = findViewById<Button>(R.id.btnImprimir)
        val layoutCarnet = findViewById<View>(R.id.layoutCarnet)

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

        btnImprimir.setOnClickListener {
            layoutCarnet.post {
                imprimirCarnet(layoutCarnet)
            }
        }

    }

    private fun capturarVistaComoBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun imprimirCarnet(view: View) {
        val bitmap = capturarVistaComoBitmap(view)
        val printHelper = PrintHelper(this)
        printHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
        printHelper.printBitmap("Carnet de Socio", bitmap)
    }
}
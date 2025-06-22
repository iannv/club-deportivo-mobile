package com.example.club_deportivo_mobile

import DataBaseHelper
import android.content.ContentValues
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class cobrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cobrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        var db = DataBaseHelper(this)


        var btnCobrar = findViewById<Button>(R.id.tv_btnCobrar)
        var btnBuscar = findViewById<Button>(R.id.tv_btnBuscar)
        var volver = findViewById<ImageView>(R.id.img_volver_buscar2)

        btnCobrar.isEnabled = false;

        var dni = findViewById<EditText>(R.id.tv_dniCampo);
        var monto = findViewById<EditText>(R.id.tv_montoCampo);


        var label1 = findViewById<TextView>(R.id.label1);
        label1.visibility = TextView.INVISIBLE;

        var label2 = findViewById<TextView>(R.id.label2);
        label2.visibility = TextView.INVISIBLE;

        var label3 = findViewById<TextView>(R.id.label3);
        label3.visibility = TextView.INVISIBLE;

        var socio = findViewById<TextView>(R.id.numeroSocio);
        socio.visibility = TextView.INVISIBLE;

        var nombre = findViewById<TextView>(R.id.nombreSocio);
        nombre.visibility = TextView.INVISIBLE;


        btnBuscar.setOnClickListener {
            //val intent = Intent(this, cobrar2::class.java)
            //startActivity(intent)

            var socioEncontrado = db.buscarClientePorDni(dni.text.toString())

            if(socioEncontrado != null){
                label1.visibility = TextView.VISIBLE;
                label2.visibility = TextView.VISIBLE;
                label3.visibility = TextView.VISIBLE;
                socio.visibility = TextView.VISIBLE;
                nombre.visibility = TextView.VISIBLE;
                btnCobrar.isEnabled = true;
                btnBuscar.isEnabled = false;
                dni.isEnabled = false;
                monto.isEnabled = false;

                if (socioEncontrado != null) {
                    socio.setText(socioEncontrado.numeroCarnet.toString())
                }
                if (socioEncontrado != null) {
                    nombre.setText(socioEncontrado.nombre + " " + socioEncontrado.apellido)
                }
            } else {
                Toast.makeText(this, "Socio no encontrado", Toast.LENGTH_SHORT).show()
            }




        }

        btnCobrar.setOnClickListener(){
            val intent = Intent(this, cobrar3::class.java)
            var socioEncontrado = db.buscarClientePorDni(dni.text.toString())
            val calendar = Calendar.getInstance()
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            calendar.add(Calendar.DAY_OF_MONTH, 30)
            val fechaFutura = formatoFecha.format(calendar.time)


            val valores = ContentValues().apply {
                put("id_cliente",socioEncontrado?.id_cliente)
                put("monto", monto.text.toString())
                put("fechaPago",formatoFecha.format(Calendar.getInstance().time))
                put("fechaVto",fechaFutura)
            }

            db.cobrarCuota(valores);

            intent.putExtra("dni",socioEncontrado?.dni)
            intent.putExtra("fecha",formatoFecha.format(Calendar.getInstance().time))
            intent.putExtra("monto",monto.text.toString())

            startActivity(intent)
        }

        volver.setOnClickListener {
            val intent = Intent(this, menuPrincipal::class.java)
            startActivity(intent)
        }
    }
}
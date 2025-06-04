package com.example.club_deportivo_mobile

import DataBaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var dbHelper = DataBaseHelper(this)

        var usuario = findViewById<EditText>(R.id.et_usuarioRegistro)
        var clave = findViewById<EditText>(R.id.et_claveRegistro)
        var ingresar = findViewById<Button>(R.id.btn_ingresar)
        var tv_mensaje = findViewById<TextView>(R.id.tv_mensaje)

        usuario.requestFocus()

        ingresar.setOnClickListener {
            val usuarioString = usuario.text.toString().trim()
            val claveString = clave.text.toString().trim()

            if(dbHelper.login(usuarioString, claveString)){
                val intent = Intent(this, menuPrincipal::class.java)
                startActivity(intent)
            } else {
                usuario.setText("")
                clave.setText("")
                usuario.requestFocus()
                tv_mensaje.setText("Usuario o contraseña incorrecta")
            }
        }
    }

}
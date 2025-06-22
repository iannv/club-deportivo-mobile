package com.example.club_deportivo_mobile

import DataBaseHelper
import Usuario
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = DataBaseHelper(this)

        var usuario = findViewById<EditText>(R.id.et_usuarioRegistro)
        var clave = findViewById<EditText>(R.id.et_claveRegistro)
        var btnRegistrarse = findViewById<Button>(R.id.btn_registrarme)
        var mensaje = findViewById<TextView>(R.id.tv_mensaje)

        btnRegistrarse.setOnClickListener {

            if (usuario.text.toString().isEmpty() || clave.text.toString().isEmpty()) mensaje.text = "Usuario y contraseña requeridos"

            else if (clave.length() < 6 || !clave.text.toString().any {it.isDigit()} || !clave.text.toString().any {it.isLetter()}) mensaje.text = "La contraseña debe contener al menos 6 caracteres, un número y una letra"

            else {
                val nuevoUsuario = usuario.text.toString()
                val nuevaClave = clave.text.toString()

                val usuarioRegistrado = Usuario(null, nuevoUsuario, nuevaClave, true, 1)
                dbHelper.registrarUsuario(usuarioRegistrado, this)

                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, login::class.java)
                startActivity(intent)
            }
        }
    }
}
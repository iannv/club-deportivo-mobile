package com.example.club_deportivo_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        var volver = findViewById<Button>(R.id.tv_btnVolver)
        var imgVolver = findViewById<ImageView>(R.id.img_volver_buscar3)

        volver.setOnClickListener {
            val intent = Intent(this, menuPrincipal::class.java)
            startActivity(intent)
        }

        imgVolver.setOnClickListener {
            val intent = Intent(this, cobrar2::class.java)
            startActivity(intent)
        }

    }
}
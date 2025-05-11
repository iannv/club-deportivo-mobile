package com.example.club_deportivo_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class actividades_socio : AppCompatActivity() {
    
    private lateinit var imgVolver: ImageView
    private lateinit var cardViewBuscar: CardView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actividades_socio)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        imgVolver = findViewById(R.id.img_volver_actividades)
        cardViewBuscar = findViewById(R.id.cardView_buscar)
        
        imgVolver.setOnClickListener {
            startActivity(Intent(this, menuPrincipal::class.java))
            finish()
        }
        
        cardViewBuscar.setOnClickListener {
            mostrarDialogoBusqueda()
        }
    }
    
    private fun mostrarDialogoBusqueda() {
        val dialogoBusqueda = android.app.AlertDialog.Builder(this)
            .setTitle("Buscar socio")
            .setMessage("La funcionalidad de búsqueda se implementará en el futuro.")
            .setPositiveButton("Aceptar", null)
            .create()
        
        dialogoBusqueda.show()
    }
}
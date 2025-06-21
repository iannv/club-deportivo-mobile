package com.example.club_deportivo_mobile

import ActividadCobrada
import Cliente
import DataBaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.*

class actividades_socio : AppCompatActivity() {
    
    private lateinit var imgVolver: ImageView
    private lateinit var etBuscarDni: EditText
    private lateinit var btnBuscarActividades: Button
    private lateinit var containerActividades: LinearLayout
    private lateinit var tvMensajeActividades: TextView
    private lateinit var dbHelper: DataBaseHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actividades_socio)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        inicializarVistas()
        configurarListeners()
        cargarTodasLasActividades()
    }
    
    private fun inicializarVistas() {
        imgVolver = findViewById(R.id.img_volver_actividades)
        etBuscarDni = findViewById(R.id.et_buscar_dni)
        btnBuscarActividades = findViewById(R.id.btn_buscar_actividades)
        containerActividades = findViewById(R.id.container_actividades)
        tvMensajeActividades = findViewById(R.id.tv_mensaje_actividades)
        dbHelper = DataBaseHelper(this)
    }
    
    private fun configurarListeners() {
        imgVolver.setOnClickListener {
            startActivity(Intent(this, menuPrincipal::class.java))
            finish()
        }
        
        btnBuscarActividades.setOnClickListener {
            val dni = etBuscarDni.text.toString().trim()
            if (dni.isNotEmpty()) {
                buscarActividadesPorDni(dni)
            } else {
                Toast.makeText(this, "Ingrese un DNI válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun cargarTodasLasActividades() {
        val todasLasActividades = dbHelper.obtenerTodasLasActividadesCobradas()
        
        if (todasLasActividades.isEmpty()) {
            mostrarMensaje("No hay actividades cobradas registradas")
            return
        }
        
        val actividadesPorCliente = todasLasActividades.groupBy { it.dni_cliente }
        
        containerActividades.removeAllViews()
        
        actividadesPorCliente.forEach { (dni, actividades) ->
            val cliente = dbHelper.buscarClientePorDni(dni)
            if (cliente != null) {
                agregarClienteConActividades(cliente, actividades)
            }
        }
        
        tvMensajeActividades.visibility = View.GONE
    }
    
    private fun buscarActividadesPorDni(dni: String) {
        val cliente = dbHelper.buscarClientePorDni(dni)
        
        if (cliente == null) {
            mostrarMensaje("Cliente no encontrado con DNI: $dni")
            return
        }
        
        val actividades = dbHelper.obtenerActividadesCobradas(dni)
        
        containerActividades.removeAllViews()
        
        if (actividades.isEmpty()) {
            mostrarMensaje("El cliente ${cliente.apellido}, ${cliente.nombre} no tiene actividades registradas")
        } else {
            agregarClienteConActividades(cliente, actividades)
            tvMensajeActividades.visibility = View.GONE
        }
    }
    
    private fun agregarClienteConActividades(cliente: Cliente, actividades: List<ActividadCobrada>) {
        val cardView = CardView(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(
            dpToPx(24), dpToPx(16), dpToPx(24), dpToPx(8)
        )
        cardView.layoutParams = layoutParams
        cardView.radius = dpToPx(8).toFloat()
        cardView.cardElevation = dpToPx(4).toFloat()
        cardView.useCompatPadding = true
        
        val mainLayout = LinearLayout(this)
        mainLayout.orientation = LinearLayout.VERTICAL
        mainLayout.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
        
        val tvNombreCliente = TextView(this)
        tvNombreCliente.text = "${cliente.apellido}, ${cliente.nombre}"
        tvNombreCliente.textSize = 16f
        tvNombreCliente.setTypeface(null, android.graphics.Typeface.BOLD)
        tvNombreCliente.setTextColor(getColor(R.color.black))
        mainLayout.addView(tvNombreCliente)
        
        val infoLayout = LinearLayout(this)
        infoLayout.orientation = LinearLayout.HORIZONTAL
        val infoLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        infoLayoutParams.topMargin = dpToPx(8)
        infoLayout.layoutParams = infoLayoutParams
        
        val tvDniLabel = TextView(this)
        tvDniLabel.text = "DNI: "
        tvDniLabel.textSize = 14f
        tvDniLabel.setTypeface(null, android.graphics.Typeface.BOLD)
        tvDniLabel.setTextColor(getColor(R.color.black))
        
        val tvDniValor = TextView(this)
        tvDniValor.text = cliente.dni
        tvDniValor.textSize = 14f
        tvDniValor.setTextColor(getColor(R.color.black))
        
        val tvSocioLabel = TextView(this)
        tvSocioLabel.text = "  |  SOCIO: "
        tvSocioLabel.textSize = 14f
        tvSocioLabel.setTypeface(null, android.graphics.Typeface.BOLD)
        tvSocioLabel.setTextColor(getColor(R.color.black))
        
        val tvSocioValor = TextView(this)
        tvSocioValor.text = if (cliente.socio) "SÍ (N°${cliente.numeroCarnet})" else "NO"
        tvSocioValor.textSize = 14f
        tvSocioValor.setTextColor(getColor(R.color.black))
        
        infoLayout.addView(tvDniLabel)
        infoLayout.addView(tvDniValor)
        infoLayout.addView(tvSocioLabel)
        infoLayout.addView(tvSocioValor)
        mainLayout.addView(infoLayout)
        
        val tvActividadesLabel = TextView(this)
        tvActividadesLabel.text = "ACTIVIDADES REALIZADAS:"
        tvActividadesLabel.textSize = 14f
        tvActividadesLabel.setTypeface(null, android.graphics.Typeface.BOLD)
        tvActividadesLabel.setTextColor(getColor(R.color.black))
        val actividadesLabelParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        actividadesLabelParams.topMargin = dpToPx(12)
        tvActividadesLabel.layoutParams = actividadesLabelParams
        mainLayout.addView(tvActividadesLabel)
        
        actividades.forEach { actividad ->
            val tvActividad = TextView(this)
            val fechaFormateada = formatearFecha(actividad.fecha)
            tvActividad.text = "• ${actividad.nombre_actividad} - $${String.format("%.2f", actividad.costo)} - $fechaFormateada"
            tvActividad.textSize = 13f
            tvActividad.setTextColor(getColor(R.color.black))
            tvActividad.setPadding(dpToPx(8), dpToPx(2), 0, dpToPx(2))
            mainLayout.addView(tvActividad)
        }
        
        val totalGastado = actividades.sumOf { it.costo.toDouble() }
        val tvTotal = TextView(this)
        tvTotal.text = "TOTAL GASTADO: $${String.format("%.2f", totalGastado)}"
        tvTotal.textSize = 14f
        tvTotal.setTypeface(null, android.graphics.Typeface.BOLD)
        tvTotal.setTextColor(getColor(R.color.black))
        val totalParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        totalParams.topMargin = dpToPx(8)
        tvTotal.layoutParams = totalParams
        mainLayout.addView(tvTotal)
        
        cardView.addView(mainLayout)
        containerActividades.addView(cardView)
    }
    
    private fun mostrarMensaje(mensaje: String) {
        containerActividades.removeAllViews()
        tvMensajeActividades.text = mensaje
        tvMensajeActividades.visibility = View.VISIBLE
    }
    
    private fun formatearFecha(fechaOriginal: String): String {
        return try {
            val formatoOriginal = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            val formatoNuevo = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val fecha = formatoOriginal.parse(fechaOriginal)
            formatoNuevo.format(fecha ?: Date())
        } catch (e: Exception) {
            fechaOriginal
        }
    }
    
    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
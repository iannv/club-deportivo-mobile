package com.example.club_deportivo_mobile

import Actividad
import ActividadCobrada
import Cliente
import DataBaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Date

class cobrar_actividad : AppCompatActivity() {

    private lateinit var etDni: EditText
    private lateinit var spinnerActividades: Spinner
    private lateinit var tvInfoCliente: TextView
    private lateinit var tvPrecioActividad: TextView
    private lateinit var btnBuscarCliente: Button
    private lateinit var btnCobrarActividad: Button
   private lateinit var imgVolver: ImageView
   private lateinit var tvMensaje: TextView
   private lateinit var dbHelper: DataBaseHelper
   
   private var clienteEncontrado: Cliente? = null
   private var actividadSeleccionada: Actividad? = null
   private var listaActividades: List<Actividad> = emptyList()

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       enableEdgeToEdge()
       setContentView(R.layout.activity_cobrar_actividad)

       ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
           val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
           v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
           insets
       }

       inicializarVistas()
       configurarListeners()
       cargarActividades()
   }

   private fun inicializarVistas() {
       etDni = findViewById(R.id.et_dni_cobrar_actividad)
       spinnerActividades = findViewById(R.id.spinner_actividades)
       tvInfoCliente = findViewById(R.id.tv_info_cliente)
       tvPrecioActividad = findViewById(R.id.tv_precio_actividad)
       btnBuscarCliente = findViewById(R.id.btn_buscar_cliente_actividad)
       btnCobrarActividad = findViewById(R.id.btn_cobrar_actividad_final)
       imgVolver = findViewById(R.id.img_volver_cobrar_actividad)
       tvMensaje = findViewById(R.id.tv_mensaje_cobrar_actividad)
       
       dbHelper = DataBaseHelper(this)
   }

   private fun configurarListeners() {
       imgVolver.setOnClickListener {
           startActivity(Intent(this, activity_menu::class.java))
           finish()
       }

       btnBuscarCliente.setOnClickListener {
           buscarCliente()
       }

       btnCobrarActividad.setOnClickListener {
           cobrarActividad()
       }

       spinnerActividades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
           override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               if (listaActividades.isNotEmpty() && position < listaActividades.size) {
                   actividadSeleccionada = listaActividades[position]
                   actualizarPrecio()
               }
           }

           override fun onNothingSelected(parent: AdapterView<*>?) {
               actividadSeleccionada = null
               tvPrecioActividad.text = ""
           }
       }
   }

   private fun cargarActividades() {
       listaActividades = dbHelper.obtenerTodasLasActividades()
       
       if (listaActividades.isEmpty()) {
           tvMensaje.text = "No hay actividades registradas"
           return
       }

       val nombresActividades = listaActividades.map { it.nombre }
       val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresActividades)
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       spinnerActividades.adapter = adapter
   }

   private fun buscarCliente() {
       val dni = etDni.text.toString().trim()
       
       if (dni.isEmpty()) {
           tvMensaje.text = "Ingrese un DNI"
           return
       }

       clienteEncontrado = dbHelper.buscarClientePorDni(dni)
       
       if (clienteEncontrado != null) {
           val cliente = clienteEncontrado!!
           tvInfoCliente.text = "${cliente.apellido}, ${cliente.nombre}\n" +
                   "DNI: ${cliente.dni}\n" +
                   if (cliente.socio) "SOCIO - Carnet N°: ${cliente.numeroCarnet}" else "NO SOCIO"
           
           tvMensaje.text = ""
           actualizarPrecio()
           btnCobrarActividad.visibility = View.VISIBLE
       } else {
           tvInfoCliente.text = ""
           tvPrecioActividad.text = ""
           btnCobrarActividad.visibility = View.GONE
           tvMensaje.text = "Cliente no encontrado"
       }
   }

   private fun actualizarPrecio() {
       if (clienteEncontrado != null && actividadSeleccionada != null) {
           val precio = if (clienteEncontrado!!.socio) 0.0f else actividadSeleccionada!!.precio
           tvPrecioActividad.text = "Precio: $${String.format("%.2f", precio)}"
       }
   }

   private fun cobrarActividad() {
       if (clienteEncontrado == null) {
           tvMensaje.text = "Debe buscar un cliente primero"
           return
       }

       if (actividadSeleccionada == null) {
           tvMensaje.text = "Debe seleccionar una actividad"
           return
       }

       val cliente = clienteEncontrado!!
       val actividad = actividadSeleccionada!!
       val costo = if (cliente.socio) 0.0f else actividad.precio

       val actividadCobrada = ActividadCobrada(
           nombre_actividad = actividad.nombre,
           nombre_cliente = cliente.nombre,
           apellido_cliente = cliente.apellido,
           dni_cliente = cliente.dni,
           fecha = Date().toString(),
           costo = costo
       )

       val resultado = dbHelper.registrarActividadCobrada(actividadCobrada, this)
       
       if (resultado) {
           Toast.makeText(this, "Actividad cobrada exitosamente", Toast.LENGTH_SHORT).show()
           limpiarCampos()
       } else {
           tvMensaje.text = "Error al registrar el cobro"
       }
   }

   private fun limpiarCampos() {
       etDni.text.clear()
       tvInfoCliente.text = ""
       tvPrecioActividad.text = ""
       tvMensaje.text = ""
       btnCobrarActividad.visibility = View.GONE
       clienteEncontrado = null
       actividadSeleccionada = null
       spinnerActividades.setSelection(0)
   }
}
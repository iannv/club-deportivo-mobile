package com.example.club_deportivo_mobile

import Cliente
import DataBaseHelper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class registro_contacto : Fragment() {
    private val viewModel: registroViewModelFragment by activityViewModels()
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registro_contacto, container, false)

        val btnRegistrar = view.findViewById<Button>(R.id.btnRegistrar)
        val limpiar = view.findViewById<Button>(R.id.btnLimpiar2)
        var email = view.findViewById<TextView>(R.id.et_email)
        var tel = view.findViewById<TextView>(R.id.et_tel)
        var domicilio = view.findViewById<TextView>(R.id.et_domicilio)
        var mensaje = view.findViewById<TextView>(R.id.mjeContacto)

        email.requestFocus()

        // Registrar cliente
        btnRegistrar.setOnClickListener {
            val cliente = Cliente(
                nombre = viewModel.nombre.value ?:"",
                apellido = viewModel.apellido.value ?:"",
                dni = viewModel.numero.value ?:"",
                aptoFisico = viewModel.esApto.value ?:false,
                socio = viewModel.esSocio.value ?:false,
                email = email.text.toString(),
                telefono = tel.text.toString(),
                domicilio = domicilio.text.toString(),
            )

            if (cliente.nombre.isEmpty() ||
                cliente.apellido.isEmpty() ||
                cliente.dni.isEmpty() ||
                cliente.domicilio.isEmpty() ||
                cliente.telefono.isEmpty() ||
                cliente.email.isEmpty()) {
                mensaje.setText("Los campos (*) son obligatorios")

            } else if (!validarEmail(cliente.email)) {
                mensaje.setText("El email ingresado no es válido")

            } else {
                mensaje.setText("")

                val dbHelper = DataBaseHelper(requireContext())
                dbHelper.registrarCliente(cliente, requireContext())

                val intent = Intent(requireContext(), carnet::class.java)
                intent.putExtra("dni", cliente.dni)
                startActivity(intent)
            }
        }


        // Limpiar campos
        limpiar.setOnClickListener {
            email.setText("")
            tel.setText("")
            domicilio.setText("")

            email.requestFocus()
        }
        return view
    }



    // Validar si el email tiene dominio correcto
    fun validarEmail(email: String): Boolean {
        val dominiosPermitidos = listOf("@gmail.com", "@hotmail.com", "@outlook.com", "@yahoo.com", "@icloud.com", "@live.com")
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && dominiosPermitidos.any { email.lowercase().endsWith(it) }
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            registro_contacto().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.example.club_deportivo_mobile

import Cliente
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import androidx.fragment.app.activityViewModels
import org.w3c.dom.Text

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class registro_datosPersonales : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_registro_datos_personales, container, false)

        val btnContinuar = view.findViewById<Button>(R.id.btnContinuar)
        var limpiar = view.findViewById<Button>(R.id.btnLimpiar)

        var nombre = view.findViewById<TextView>(R.id.et_nombre)
        var apellido = view.findViewById<TextView>(R.id.et_apellido)
        var dni = view.findViewById<TextView>(R.id.et_numero)
        var esApto = view.findViewById<CheckBox>(R.id.chk_apto)
        var esSocio = view.findViewById<CheckBox>(R.id.chk_socio)
        var mensaje = view.findViewById<TextView>(R.id.mjeContacto2)

        nombre.requestFocus()

        btnContinuar.setOnClickListener {
            if(nombre.text.isEmpty() || apellido.text.isEmpty() || dni.text.isEmpty()){
                mensaje.setText("Los campos (*) son obligatorios")

            } else if (dni.text.length != 8){
                mensaje.setText("El DNI ingresado no es válido")

            } else if (!validarSoloString(nombre.text.toString())){
                mensaje.setText("El nombre no puede contener números")

            } else if (!validarSoloString(apellido.text.toString())) {
                mensaje.setText("El apellido no puede contener números")

            } else {
                // Capturar datos de los campos y guardarlos en el ViewModel
                viewModel.nombre.value = nombre.text.toString()
                viewModel.apellido.value = apellido.text.toString()
                viewModel.numero.value = dni.text.toString()
                viewModel.esApto.value = esApto.isChecked
                viewModel.esSocio.value = esSocio.isChecked

                mensaje.setText("")

                val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewPager2)
                viewpager.currentItem = 1
            }
        }

        // Limpiar campos
        limpiar.setOnClickListener {
            nombre.setText("")
            apellido.setText("")
            dni.setText("")
            esApto.isChecked = false
            esSocio.isChecked = false

            nombre.requestFocus()
        }
        return view
    }


    // Validar si la palabra solo contiene letras
    fun validarSoloString(palabra : String): Boolean {
        if (palabra.chars().allMatch { Character.isLetter(it) }){
            return true
        } else {
            return false
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            registro_datosPersonales().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
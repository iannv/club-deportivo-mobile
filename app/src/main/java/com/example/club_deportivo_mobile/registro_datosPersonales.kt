package com.example.club_deportivo_mobile

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
import androidx.viewpager2.widget.ViewPager2

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [registro_datosPersonales.newInstance] factory method to
 * create an instance of this fragment.
 */
class registro_datosPersonales : Fragment() {
    // TODO: Rename and change types of parameters
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
        var tipo = view.findViewById<Spinner>(R.id.spinner_tipo)
        var numero = view.findViewById<TextView>(R.id.et_numero)
        var esApto = view.findViewById<CheckBox>(R.id.chk_apto)
        var esSocio = view.findViewById<CheckBox>(R.id.chk_socio)

        btnContinuar.setOnClickListener {
            val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewPager2)
            viewpager.currentItem = 1
        }

        // Limpiar campos
        limpiar.setOnClickListener {
            nombre.setText("")
            apellido.setText("")
            tipo.setSelection(0)
            numero.setText("")
            esApto.isChecked = false
            esSocio.isChecked = false

            nombre.requestFocus()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment registro_datosPersonales.
         */
        // TODO: Rename and change types and number of parameters
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
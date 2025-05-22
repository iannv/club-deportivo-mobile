package com.example.club_deportivo_mobile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class registroViewModelFragment : ViewModel() {
    val nombre = MutableLiveData<String>()
    val apellido = MutableLiveData<String>()
    val tipoDoc = MutableLiveData<String>()
    val numero = MutableLiveData<String>()
    val esApto = MutableLiveData<Boolean>()
    val esSocio = MutableLiveData<Boolean>()
    val email = MutableLiveData<String>()
    val telefono = MutableLiveData<String>()
    val domicilio = MutableLiveData<String>()
}


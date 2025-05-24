data class Cliente (
    val id_cliente : Int? = 0,
    val nombre : String,
    val apellido : String,
    val dni : String,
    val domicilio : String,
    val telefono : String,
    val email : String,
    val aptoFisico : Boolean,
    val socio : Boolean,
    val numeroCarnet : Int? = 0,
    val fechaAlta : String? = null
)

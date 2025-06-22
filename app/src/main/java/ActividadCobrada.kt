data class ActividadCobrada(
    val id_actividad_cobrada: Int? = 0,
    val nombre_actividad: String,
    val nombre_cliente: String,
    val apellido_cliente: String,
    val dni_cliente: String,
    val costo: Float,
    val fecha: String
)
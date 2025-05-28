import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class DataBaseHelper(contexto: Context) : SQLiteOpenHelper(contexto, "clubDeportivo.db",null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_ROL_TABLE)
        db?.execSQL(CREATE_USUARIO_TABLE)
        db?.execSQL(CREATE_CLIENTE_TABLE)
        db?.execSQL(CREATE_ACTIVIDAD_TABLE)
        db?.execSQL(CREATE_CUOTA_TABLE)

        db?.execSQL("INSERT INTO rol(nombre) VALUES('administrador')")
        db?.execSQL("INSERT INTO usuario(nombreUsuario, clave, activo, rol) VALUES('admin', '123', 1, 1)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS rol")
        db?.execSQL("DROP TABLE IF EXISTS usuario")
        db?.execSQL("DROP TABLE IF EXISTS cliente")
        db?.execSQL("DROP TABLE IF EXISTS actividad")
        db?.execSQL("DROP TABLE IF EXISTS cuota")
        onCreate(db)
    }

    companion object{
        private const val CREATE_ROL_TABLE = "CREATE TABLE rol(id_rol INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)"

        private const val CREATE_USUARIO_TABLE = "CREATE TABLE usuario(id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreUsuario TEXT UNIQUE, " +
                "clave TEXT, " +
                "activo BOOL DEFAULT TRUE, " +
                "rol INTEGER, " +
                "FOREIGN KEY (rol) REFERENCES rol(id_rol))"

        private const val CREATE_CLIENTE_TABLE = "CREATE TABLE cliente(id_cliente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "apellido TEXT, " +
                "dni TEXT, " +
                "domicilio TEXT, " +
                "telefono TEXT, " +
                "email TEXT, " +
                "aptoFisico BOOL, " +
                "socio BOOL, " +
                "fechaAlta TEXT, " +
                "numeroCarnet INTEGER)"

        private const val CREATE_ACTIVIDAD_TABLE = "CREATE TABLE actividad(id_actividad INTEGER PRIMARY KEY, " +
                "nombre TEXT, " +
                "cupo INTEGER, " +
                "precio FLOAT)"

        private const val CREATE_CUOTA_TABLE = "CREATE TABLE cuota(id_cuota INTEGER PRIMARY KEY, " +
                "id_cliente INTEGER, " +
                "monto FLOAT, " +
                "fechaPago TEXT, " +
                "fechaVto TEXT, " +
                "FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente))"
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    // ****** LOGIN ****** //
    fun login(usuario: String, clave: String): Boolean {
        val bd = this.readableDatabase
        val cursor = bd.rawQuery("SELECT * FROM usuario WHERE nombreUsuario = ? AND clave = ?", arrayOf(usuario, clave))
        var existe = cursor.count > 0
        return existe
    }

    // ****** REGISTRAR CLIENTE ****** //
    fun registrarCliente(cliente: Cliente, context: Context) {
        val bd = this.writableDatabase
        val values = ContentValues()

        val calendar = Calendar.getInstance()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaAlta = formatoFecha.format(calendar.time)

        values.put("nombre", cliente.nombre)
        values.put("apellido", cliente.apellido)
        values.put("dni", cliente.dni)
        values.put("domicilio", cliente.domicilio)
        values.put("telefono", cliente.telefono)
        values.put("email", cliente.email)
        values.put("aptoFisico", cliente.aptoFisico)
        values.put("socio", cliente.socio)
        values.put("fechaAlta", fechaAlta)

        val resultado = bd.insert("cliente", null, values)

        if (resultado != -1L) {
            val idGenerado = resultado.toInt()
            val actualizarCarnet = ContentValues()
            actualizarCarnet.put("numeroCarnet", idGenerado)
            bd.update("cliente", actualizarCarnet, "id_cliente = ?", arrayOf(idGenerado.toString()))

            Toast.makeText(context, "Cliente registrado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error al registrar cliente", Toast.LENGTH_SHORT).show()
        }

        bd.close()
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////

}
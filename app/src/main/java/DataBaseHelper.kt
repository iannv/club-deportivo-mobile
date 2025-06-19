import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import dto.ClienteDTO
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
                "dni TEXT UNIQUE, " +
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

    // ****** REGISTRO DE NEUVO USUARIO ****** //
    fun registrarUsuario(usuario: Usuario, contexto: Context) {
        val bd = this.writableDatabase
        val values = ContentValues()

        values.put("nombreUsuario", usuario.nombreUsuario)
        values.put("clave", usuario.clave)
        values.put("activo", usuario.activo)
        values.put("rol", usuario.rol)

        bd.insert("usuario", null, values)
        bd.close()
    }

    // ****** LOGIN ****** //
    fun login(usuario: String, clave: String): Boolean {
        val bd = this.readableDatabase
        val cursor = bd.rawQuery("SELECT * FROM usuario WHERE nombreUsuario = ? AND clave = ?", arrayOf(usuario, clave))
        var existe = cursor.count > 0
        cursor.close()
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

        } else {
            Toast.makeText(context, "Error al registrar cliente", Toast.LENGTH_SHORT).show()
        }

        bd.close()
    }

    // Valida si un cliente existe por el DNI antes de registrarlo nuevamente
    fun validarDniExistente(dni: String) : Boolean {
        val bd = this.readableDatabase
        val cursor = bd.rawQuery("SELECT * FROM cliente WHERE dni = ?", arrayOf(dni))
        var existe = cursor.count > 0
        cursor.close()
        return existe
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////


    // Buscar cliente por DNI
    fun buscarClientePorDni(dni: String): Cliente? {
        val bd = this.readableDatabase
        val cursor = bd.rawQuery("SELECT * FROM cliente WHERE dni = ?", arrayOf(dni))

        var cliente: Cliente? = null

        if (cursor.moveToFirst()) {
            cliente = Cliente(
                id_cliente = cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                domicilio = cursor.getString(cursor.getColumnIndexOrThrow("domicilio")),
                telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                aptoFisico = cursor.getInt(cursor.getColumnIndexOrThrow("aptoFisico")) == 1,
                socio = cursor.getInt(cursor.getColumnIndexOrThrow("socio")) == 1,
                numeroCarnet = cursor.getInt(cursor.getColumnIndexOrThrow("numeroCarnet")),
                fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fechaAlta"))
            )
        }

        cursor.close()
        bd.close()
        return cliente
    }

    // Obtener la cuota más reciente de un cliente
    fun obtenerUltimaCuota(idCliente: Int): Float? {
        val bd = this.readableDatabase
        val cursor = bd.rawQuery(
            "SELECT monto FROM cuota WHERE id_cliente = ? ORDER BY fechaPago DESC LIMIT 1",
            arrayOf(idCliente.toString())
        )

        var monto: Float? = null
        if (cursor.moveToFirst()) {
            monto = cursor.getFloat(cursor.getColumnIndexOrThrow("monto"))
        }

        cursor.close()
        bd.close()
        return monto
    }


    fun listadoDeVencimientos(fecha:String):List<ClienteDTO> {
        val db: SQLiteDatabase = readableDatabase
        val sql: String = "SELECT c.id_cliente,c.nombre,c.apellido,c.numeroCarnet " +
                "FROM cliente AS c INNER JOIN cuota AS k ON c.id_cliente = k.id_cliente " +
                "WHERE k.fechaVto = " + "'" + fecha + "'"

        val cursor: Cursor = db.rawQuery(sql, null);

        var lista: MutableList<ClienteDTO> = mutableListOf()

        while (cursor.moveToNext()) {
            var cliente: ClienteDTO? = null

            cliente = ClienteDTO(
                id_cliente = cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                numeroCarnet = cursor.getInt(cursor.getColumnIndexOrThrow("numeroCarnet"))
            )
            lista.add(cliente)
        }
        cursor.close()
        db.close()
        return lista
    }

}








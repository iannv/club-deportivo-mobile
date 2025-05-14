import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class DataBaseHandler(contexto: Context) : SQLiteOpenHelper(contexto, "clubDeportibo.db",null, 1) {
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
                "nombreUsuario TEXT, " +
                "clave TEXT, " +
                "activo BOOL DEFAULT TRUE, " +
                "rol INTEGER, " +
                "FOREIGN KEY (rol) REFERENCES rol(id_rol))"

        private const val CREATE_CLIENTE_TABLE = "CREATE TABLE cliente(id_cliente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "apellido TEXT, " +
                "tipoDoc TEXT, " +
                "dni TEXT, " +
                "domicilio TEXT, " +
                "telefono TEXT, " +
                "email TEXT, " +
                "aptoFisico BOOL, " +
                "socio BOOL, " +
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
    // ****** INSERTAR USUARIO ****** //
    fun insertarUsuario(usuario: Usuario):String{
        val p0 = this.writableDatabase
        var contenedor = ContentValues()
        contenedor.put("nombre", usuario.nombreUsuario)
        contenedor.put("clave", usuario.clave)
        contenedor.put("activo", usuario.activo)
        contenedor.put("rol", usuario.rol)

        var resultado = p0.insert("usuario ", null, contenedor)
        if (resultado == -1.toLong()){
            return "Falló la carga del usuario"
        } else{
            return "Carga del usuario exitoso"
        }
    }

    // ****** OBTENER USUARIO ****** //
    fun obtenerUsuarios():ArrayList<Usuario>{return ArrayList()}

    // ****** ACTUALIZAR USUARIO ****** //
    fun actualizarUsuario(usuario: Usuario):String{return ""}


    ////////////////////////////////////////////////////////////////////////////////////////////////

}
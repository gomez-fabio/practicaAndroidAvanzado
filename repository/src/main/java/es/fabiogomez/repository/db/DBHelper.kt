package es.fabiogomez.repository.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


internal fun build(context: Context, name: String, version: Int) : DBHelper {
    return DBHelper(context, name, null, version)
}

internal class DBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)

        // Needed for on cascade deletion to work properly
        db?.setForeignKeyConstraintsEnabled(true)

    }

    override fun onCreate(db: SQLiteDatabase?) {
        DBConstants.CREATE_DATABASE_SCRIPTS.forEach { db?.execSQL(it) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // aqui iría el codigo para actualizar el modelo de datos
        // en los momentos de upgrade la propia app con el nuevo modelo de datos
    }

}

// helpers
internal fun convert(boolean: Boolean): Int {
    if (boolean) {
        return 1
    }
    return 0
}

internal fun convert(int: Int): Boolean {
    if (int == 0){
        return false
    }
    return true
}

fun stringToFloat(string: String): Float{

    var latOrLong = 0f

    var cleanedString: String = string.replace( ",", "")

    cleanedString = cleanedString.replace(" ", "")

    if (cleanedString.length > 0) {
        latOrLong = cleanedString.toFloat()
    }

    return latOrLong

}
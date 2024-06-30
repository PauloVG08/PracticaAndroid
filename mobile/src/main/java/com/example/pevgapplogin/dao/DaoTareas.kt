package com.example.pevgapplogin.dao
import android.content.Context
import android.database.Cursor
import com.example.pevgapplogin.config.DbConnection
import com.example.pevgapplogin.models.TareaModel

class DaoTareas(context: Context) {
    private val dbConn = DbConnection(context)
    private val tableName = "Tareas"
    fun insert(data: TareaModel) {
        val db = dbConn.writableDatabase
        val registrado = db.insert(tableName, null, data.mapForInsert())
        db.close()
    }
    fun update(data: TareaModel) {
        val db = dbConn.writableDatabase
        db.update(tableName, data.mapForUpdate(), "id=?", arrayOf(data.id.toString()))
        db.close()
    }
    fun delete(data: TareaModel) {
        val db = dbConn.writableDatabase
        db.delete(tableName, "id=?", arrayOf(data.id.toString()))
        db.close()
    }

    fun getAll(): List<TareaModel> {
        val db = dbConn.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $tableName", null)
        val data = mutableListOf<TareaModel>()
        with(cursor) {
            while (moveToNext()) {
                val item = TareaModel(
                    getInt(getColumnIndexOrThrow("id")),
                    getString(getColumnIndexOrThrow("nombre"))
                )
                data.add(item)
            }
            close()
        }
        db.close()
        return data
    }
    fun getById(pId: Int): TareaModel? {
        val db = dbConn.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $tableName WHERE id=?", arrayOf(pId.toString()))
        val data = mutableListOf<TareaModel>()
        with(cursor) {
            while (moveToNext()) {
                val item = TareaModel(
                    getInt(getColumnIndexOrThrow("id")),
                    getString(getColumnIndexOrThrow("nombre"))
                )
                data.add(item)
            }
            close()
        }
        db.close()
        if(data.isEmpty())
            return data.first()
        return null
    }
}

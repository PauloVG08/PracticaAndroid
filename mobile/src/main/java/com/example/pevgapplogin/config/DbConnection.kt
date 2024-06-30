package com.example.pevgapplogin.config
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbConnection(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Configuración inicial de la BD
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "IDGSDB.db"
    }
    // Método tras la creación de la BD
    override fun onCreate(db: SQLiteDatabase?) {
// Crear la estructura de la base de datos
        db?.execSQL("CREATE TABLE Configuracion (id TEXT,jsondata TEXT)")
        db?.execSQL("INSERT INTO Configuracion (id,jsondata) VALUES ('SESSION',NULL)")
        db?.execSQL("CREATE TABLE Tareas (id INTEGER PRIMARY KEY, nombre TEXT)")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
// Manejar la actualización de la base de datos, si es necesario
    }
}
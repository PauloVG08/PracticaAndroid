package com.example.pevgapplogin.models
import android.content.ContentValues

class TareaModel(val id: Int, var nombre: String) {
    fun mapForInsert() : ContentValues {
        return ContentValues().apply {
            put("nombre", nombre)
        }
    }
    fun mapForUpdate() : ContentValues {
        return ContentValues().apply {
            put("nombre", nombre)
        }
    }
}
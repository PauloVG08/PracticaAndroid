package com.example.pevgapplogin.models

import com.google.gson.annotations.SerializedName

data class UsuarioModel(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("nombre")val nombre: String,
    @SerializedName("sexo") val sexo: String)
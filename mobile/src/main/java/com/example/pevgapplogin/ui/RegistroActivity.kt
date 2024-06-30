package com.example.pevgapplogin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.pevgapplogin.MainActivity
import com.example.pevgapplogin.R
import com.example.pevgapplogin.apiservice.RetrofitClient
import com.example.pevgapplogin.models.RegistroModel
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {
    lateinit var nombreInputLayout: TextInputLayout
    lateinit var nombreInput: TextInputEditText
    lateinit var emailInputLayout: TextInputLayout
    lateinit var emailInput: TextInputEditText
    lateinit var contrasenaInputLayout: TextInputLayout
    lateinit var contrasenaInput: TextInputEditText
    lateinit var terminosInputLayout: TextInputLayout
    lateinit var terminosInput: MaterialCheckBox
    lateinit var sexoInputLayout: TextInputLayout
    var sexoOptions: Array<String> = arrayOf("Seleccionar sexo","Hombre", "Mujer")
    lateinit var sexoInput: Spinner
    lateinit var contexto: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contexto = this
        setContentView(R.layout.activity_registro)
        nombreInputLayout = findViewById<TextInputLayout>(R.id.nombreInputLayout)
        nombreInput = findViewById<TextInputEditText>(R.id.nombreEditText)
        contrasenaInputLayout = findViewById<TextInputLayout>(R.id.contrasenaInputLayout)
        contrasenaInput = findViewById<TextInputEditText>(R.id.contrasenaEditText)
        emailInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        emailInput = findViewById<TextInputEditText>(R.id.emailEditText)
        terminosInputLayout = findViewById<TextInputLayout>(R.id.terminosInputLayout)
        terminosInput = findViewById<MaterialCheckBox>(R.id.terminosCheckbox)
        sexoInputLayout = findViewById<TextInputLayout>(R.id.sexoInputLayout)
        sexoInput = findViewById<Spinner>(R.id.sexoInput)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexoOptions)
        // Especificar el layout a usar cuando la lista de opciones aparece
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Aplicar el adapter al Spinner
        sexoInput.adapter = adapter
        val registrarButton = findViewById<Button>(R.id.registrarButton)
        registrarButton.setOnClickListener{
            if(formValido()){
                // registrar()
                val params = RegistroModel(nombreInput.text.toString(), emailInput.text.toString(), contrasenaInput.text.toString(), sexoInput.selectedItemPosition.toString())
                registrarme(params)
            } else {
                Toast.makeText(this, "Existe información incorrecta", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun registrarme(parametros: RegistroModel){
        RetrofitClient.instance.postRegistrar(parametros).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    Toast.makeText(this@RegistroActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegistroActivity, MainActivity::class.java)
                    intent.putExtra("email", emailInput.text.toString())
                    startActivity(intent)
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(contexto, "${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show();
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle failure
                Log.e("MainActivity", "Failure: ${t.message}")
            }
        })
    }
    fun formValido() : Boolean {
        var valido = true
        if(nombreInput.text.toString().isEmpty()){
            nombreInputLayout.error = "Campo obligatorio"
            valido = false
        } else {
            nombreInputLayout.error = ""
        }
        if(contrasenaInput.text.toString().isEmpty()){
            contrasenaInputLayout.error = "Campo obligatorio"
            valido = false
        } else {
            contrasenaInputLayout.error = ""
        }
        if(emailInput.text.toString().isEmpty()){
            emailInputLayout.error = "Campo obligatorio"
            valido = false
        } else {
            emailInputLayout.error = ""
        }
        if(!terminosInput.isChecked){
            terminosInputLayout.error = "Es necesario aceptar los términos"
            valido = false
        } else {
            terminosInputLayout.error = ""
        }
        var valorTexto = sexoInput.selectedItem.toString()
        Log.i("PAULO",valorTexto)
        var valorId = sexoInput.selectedItemId.toString()
        Log.i("PAULO",valorId)
        var valorIndice = sexoInput.selectedItemPosition.toString()
        if(valorIndice == "0"){
            sexoInputLayout.error = "Indica tu sexo"
            valido = false
        } else {
            sexoInputLayout.error = ""
        }
        return valido
    }
}
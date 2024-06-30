package com.example.pevgapplogin.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.pevgapplogin.R
import com.example.pevgapplogin.config.SessionManager
import com.example.pevgapplogin.dao.DaoTareas
import com.example.pevgapplogin.models.TareaModel
import com.google.android.material.textfield.TextInputLayout

class TareasActivity : AppCompatActivity() {
    private lateinit var daoTareas: DaoTareas
    private lateinit var tableLayout: TableLayout
    private lateinit var formData: TareaModel
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tareas)
        tableLayout = findViewById(R.id.tableLayout)
        daoTareas = DaoTareas(this)
        sessionManager = SessionManager(this)

        if(sessionManager.getUserId() !== null){
            val txtTitulo = findViewById<TextView>(R.id.txtTitulo)
            txtTitulo.text = "Tareas de ${sessionManager.getUser()?.nombre}"
        }

        val btnAgregar = findViewById<FloatingActionButton>(R.id.btnAgregar)
        btnAgregar.setOnClickListener {
            showForm(TareaModel(0, ""))
        }
        recargarTabla()
    }

    fun recargarTabla() {
        val tareas = daoTareas.getAll()
        tableLayout.removeAllViews()
        for (tarea in tareas){
            val textViewId = TextView(this)
            textViewId.text = tarea.id.toString()
            textViewId.setPadding(8, 8, 8, 8)

            val textViewNombre = TextView(this)
            textViewNombre.text = tarea.nombre
            textViewNombre.setPadding(8, 8, 8, 8)

            val btnEditar = Button(this)
            btnEditar.text = "Editar"

            val btnEliminar = Button(this)
            btnEliminar.text = "Eliminar"

            btnEditar.setOnClickListener {
                showForm(tarea)
            }

            btnEliminar.setOnClickListener{
                eliminarTarea(tarea)
            }

            val tableRow = TableRow(this)
            tableRow.addView(textViewId)
            tableRow.addView(textViewNombre)

            tableRow.addView(btnEditar)
            tableRow.addView(btnEliminar)

            tableLayout.addView(tableRow)
        }
    }

    fun showForm(pTareaModel: TareaModel){
        formData = pTareaModel
        val dialogView = layoutInflater.inflate(R.layout.tarea_form_dialog, null)
        val editTextNombre = dialogView.findViewById<EditText>(R.id.dialogEditTextNombre)
        val dialogNombreInputLayout = dialogView.findViewById<TextInputLayout>(R.id.dialogNombreInputLayout)
        editTextNombre.setText(formData.nombre)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Formulario").setView(dialogView)
            .setPositiveButton("Guardar", null)
            .setNegativeButton("Cancelar") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if(editTextNombre.text.isEmpty()){
                dialogNombreInputLayout.error = "El campo no puede estar vacío"
            } else {
                // Obtener el valor del campo nombre
                alertDialog.dismiss()
                formData.nombre = editTextNombre.text.toString()
                guardarTarea()
            }
        }
    }

    fun eliminarTarea(pTareaModel: TareaModel) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Eliminar")
            .setMessage("¿Estás seguro de que deseas eliminar esta tarea?")
            .setPositiveButton("Eliminar") { _, _ ->
                daoTareas.delete(pTareaModel)
                recargarTabla()
            }
            .setNegativeButton("Cancelar") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.create()
        alertDialog.show()
    }

    fun guardarTarea() {
        // Si es un 0 significa que es un registro
        if(formData.id == 0){
            daoTareas.insert(formData)
            recargarTabla()
        } else {
            daoTareas.update(formData)
            recargarTabla()
        }
    }
}
package com.example.daniel.fitgo.activities.UsserRutinas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daniel.fitgo.R
import com.example.daniel.fitgo.models.Rutina
import com.example.mylibrary.ToolbarActivity
import kotlinx.android.synthetic.main.activity_my_rutina.*

class MyRutina : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_rutina)

        val intent: Intent= intent
        val nombre = intent.getStringExtra("nombre")
        val tipo = intent.getStringExtra("tipo")
        val descripcion = intent.getStringExtra("descripcion")
        val ejercicios = intent.getStringExtra("ejercicios")
        txtNombre.text= nombre
        txtTipo.text= tipo
        txtDescripcion.text=descripcion
        txtEjercicios.text=ejercicios

    }
}

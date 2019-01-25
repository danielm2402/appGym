package com.example.daniel.fitgo.activities.UsserRutinas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daniel.fitgo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_rutina_fit_go.*

class RutinaFitGo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutina_fit_go)

        val intent: Intent = intent
        val nombre = intent.getStringExtra("nombreR")
        val tipo = intent.getStringExtra("tipoR")
        val descripcion = intent.getStringExtra("descripcionR")
        val entrenador = intent.getStringExtra("entrenadorR")
        val duracion = intent.getStringExtra("duracionR")
        val imagen = intent.getStringExtra("imagenR")
        val ejercicios = intent.getStringExtra("ejerciciosR")

        txtNombre.text=nombre
        txtDescripcion.text=descripcion
        txtEjercicios.text=ejercicios
        txtEntrenador.text=entrenador
        txtTipo.text=tipo
        txtDuracion.text=duracion
        Picasso.with(this).load(imagen).fit().into(imageView)
    }
}

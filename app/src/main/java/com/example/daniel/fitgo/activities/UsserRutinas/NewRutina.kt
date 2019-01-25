package com.example.daniel.fitgo.activities.UsserRutinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daniel.fitgo.*
import com.example.daniel.fitgo.adapters.RutinaAdapter
import com.example.daniel.fitgo.models.Rutina
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_new_rutina.*
import kotlinx.android.synthetic.main.fragment_agregar_rutinas.*
import java.util.*

class NewRutina : AppCompatActivity() {

    private lateinit var  adapter: RutinaAdapter
    private val rutinasList: ArrayList<Rutina> = ArrayList()

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var curretUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var rutDBRef: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_rutina)

         setUpRutDB()
        setUpCurrentUser()
        //setUpRecyclerView()
        setUpAddBtn()

        //val rut= Rutina(curretUser.uid)
        //suscribeToRutinas(rut)

        editTextNombre.validate {
            editTextNombre.error= if(isNoNull(it))null else "Por favor completar campo"
        }
        editDescripcion.validate {
            editDescripcion.error= if(isNoNull(it))null else "Por favor completar campo"
        }
        editTipo.validate {
            editTipo.error= if(isNoNull(it))null else "Por favor completar campo"
        }

    }




    private fun setUpAddBtn() {

        btnbd.setOnClickListener{


            var nombre = editTextNombre.text.toString()
            val tipo = editTipo.text.toString()
            val descripcion= editDescripcion.text.toString()
            val ejercicios = editTextEjercicios.text.toString()

if(nombre!="" && tipo!=""&& descripcion!="" && ejercicios!="")
{

    val rutina = Rutina(curretUser.uid,nombre,tipo,descripcion,ejercicios)
    SaveRutina(rutina)
}
            else{
    toast("Verifique ha llenado todos los campos correctamente")
            }



        }

        btncan.setOnClickListener{
            finish()
        }


    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        adapter = RutinaAdapter(rutinasList,curretUser.uid, this!!)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator= DefaultItemAnimator()
        recyclerView.adapter = adapter
    }

    private fun setUpCurrentUser() {
        curretUser = mAuth.currentUser!!
    }

    private fun setUpRutDB() {
        rutDBRef = store.collection("MisRutinas")
    }

    public fun SaveRutina(rutina: Rutina)
    {
        val newRutina = HashMap<String, Any>()
        newRutina["authorId"]= rutina.authorId
        newRutina["nombre"]= rutina.nombre
        newRutina["tipo"]= rutina.tipo
        newRutina["descripcion"]=rutina.descripcion
        newRutina["ejercicios"]=rutina.ejercicios
        rutDBRef.add(newRutina).addOnCompleteListener{
            toast("Rutina agregada")
            finish()
        }
                .addOnFailureListener{
                    toast("Intente de nuevo")
                }
    }

    private fun suscribeToRutinas(rutina: Rutina){
        rutDBRef.addSnapshotListener(object: EventListener, com.google.firebase.firestore.EventListener<QuerySnapshot> {
            override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                exception?.let{
                    toast("Exception!")
                    return
                }
                snapshot?.let {

                    rutinasList.clear()
                    val rutinas = it.toObjects(Rutina::class.java)
                    val rutValida: ArrayList<Rutina> = ArrayList()

                    for(item in rutinas)
                    {
                        if(item.authorId==curretUser.uid)
                        {
                            rutValida.add(item)

                        }
                    }

                    rutinasList.addAll(rutValida)
                    adapter.notifyDataSetChanged()


                }
            }


        })
    }

}

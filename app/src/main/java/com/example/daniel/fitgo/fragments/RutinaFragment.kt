package com.example.daniel.fitgo.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.daniel.fitgo.R
import com.example.daniel.fitgo.adapters.RutinaAdapter
import com.example.daniel.fitgo.adapters.RutinaFitAdapter
import com.example.daniel.fitgo.models.RutinaFitGo
import com.example.daniel.fitgo.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_rutina.view.*
import java.util.*


class RutinaFragment : Fragment() {


    private lateinit var _view: View
    private lateinit var  adapter: RutinaFitAdapter
    private val rutinasList: ArrayList<RutinaFitGo> = ArrayList()

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var curretUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var rutDBRef: CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view= inflater.inflate(R.layout.fragment_rutina, container, false)

        setUpRutDB()
        setUpCurrentUser()
        setUpRecyclerView()


        val rut= RutinaFitGo(curretUser.uid)
        suscribeToRutinas(rut)


        return _view


    }



    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        adapter = RutinaFitAdapter(rutinasList,curretUser.uid, context!!)
        _view.recyclerView1 .setHasFixedSize(true)
        _view.recyclerView1.layoutManager = layoutManager
        _view.recyclerView1.itemAnimator= DefaultItemAnimator()
        _view.recyclerView1.adapter = adapter
    }

    private fun setUpCurrentUser() {
        curretUser = mAuth.currentUser!!
    }

    private fun setUpRutDB() {
        rutDBRef = store.collection("RutinasFitGo")
    }

    private fun SaveRutina()
    {
        val newRutina = HashMap<String, Any>()
        newRutina["authorId"]= ""
        newRutina["descripcion"]= "Rutina dieñada par atacar deltoides."
        newRutina["tipo"]= "Hipertrofia"
        newRutina["nombre"]= "Entrenamiento deltoides"
        newRutina["entrenador"]= "Prof. J Valencia"
        newRutina["duracion"]= "1 hora"
        newRutina["ejercicios"]= """
            1. Press militar (10 reps - 4 series).
            2. Press arnold (10 reps - 4 series).
            3. Abdución con mancuernas (10 reps - 4 series).

            Nota: Adecuar repeticiones y peso teniendo en cuenta su nivel de entrenamiento.
        """.trimIndent()
        newRutina["urlimagen"]= "https://cdn.hsnstore.com/blog/wp-content/uploads/2013/10/Imagen-destacada-Blog11-1.jpg"
        rutDBRef.add(newRutina).addOnCompleteListener{
            activity!!.toast("Rutina agregada")
        }
                .addOnFailureListener{
                    activity!!.toast("Intente de nuevo")
                }
    }

    private fun suscribeToRutinas(rutina: RutinaFitGo){
        rutDBRef.addSnapshotListener(object: EventListener, com.google.firebase.firestore.EventListener<QuerySnapshot> {
            override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                exception?.let{
                    activity!!.toast("Exception!")
                    return
                }
                snapshot?.let {

                    rutinasList.clear()
                    val rutinas = it.toObjects(RutinaFitGo::class.java)
                    rutinasList.addAll(rutinas)
                    adapter.notifyDataSetChanged()

                }
            }


        })
    }

}

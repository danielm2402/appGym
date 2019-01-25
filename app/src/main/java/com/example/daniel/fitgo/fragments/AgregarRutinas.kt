package com.example.daniel.fitgo.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.daniel.fitgo.R
import com.example.daniel.fitgo.activities.UsserRutinas.NewRutina
import com.example.daniel.fitgo.adapters.RutinaAdapter
import com.example.daniel.fitgo.goToActivity
import com.example.daniel.fitgo.models.Rutina
import com.example.daniel.fitgo.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_agregar_rutinas.view.*
import java.util.*

class AgregarRutinas : Fragment() {
    private lateinit var _view: View
    private lateinit var  adapter: RutinaAdapter
    private val rutinasList: ArrayList<Rutina> = ArrayList()

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var curretUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var rutDBRef: CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view= inflater.inflate(R.layout.fragment_agregar_rutinas, container, false)

        setUpRutDB()
        setUpCurrentUser()
        setUpRecyclerView()
        setUpAddBtn()

        val rut= Rutina(curretUser.uid)
        suscribeToRutinas(rut)


return _view
    }

    private fun setUpAddBtn() {

        _view.buttonAdd.setOnClickListener{

            activity!!.goToActivity<NewRutina>{}

        }


    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        adapter = RutinaAdapter(rutinasList,curretUser.uid, context!!)
        _view.recyclerView.setHasFixedSize(true)
        _view.recyclerView.layoutManager = layoutManager
        _view.recyclerView.itemAnimator= DefaultItemAnimator()
        _view.recyclerView.adapter = adapter
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
        newRutina["descripcion"]= rutina.descripcion
        newRutina["tipo"]= rutina.tipo
        newRutina["ejercicios"]=rutina.ejercicios
        rutDBRef.add(newRutina).addOnCompleteListener{
            activity!!.toast("Rutina agregada")
        }
                .addOnFailureListener{
                    activity!!.toast("Intente de nuevo")
                }
    }

    private fun suscribeToRutinas(rutina: Rutina){
        rutDBRef.addSnapshotListener(object: EventListener, com.google.firebase.firestore.EventListener<QuerySnapshot> {
            override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                exception?.let{
                    activity!!.toast("Exception!")
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

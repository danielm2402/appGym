package com.example.daniel.fitgo.adapters

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daniel.fitgo.CostomItemClickListener
import com.example.daniel.fitgo.R
import com.example.daniel.fitgo.activities.UsserRutinas.MyRutina
import com.example.daniel.fitgo.inflate
import com.example.daniel.fitgo.models.Rutina
import com.example.daniel.fitgo.models.RutinaFitGo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add_rutina.view.*
import kotlinx.android.synthetic.main.fragment_cardview.view.*

class RutinaFitAdapter(val items: List<RutinaFitGo>, val userId: String, context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private lateinit var listener: View.OnClickListener
    var mContext = context
    private val MiRutinas=1
    private val other=2
    private val layoutRut = R.layout.fragment_cardview
    override fun onClick(v: View?) {

    }


    override fun getItemViewType(position: Int) =  MiRutinas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder1(parent.inflate(layoutRut))
    }

    override fun getItemCount()=items.size



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder1).bind(items[position])
        holder.setOnClickListener(object : CostomItemClickListener {
            override fun onCostomItemClickListener(view: View, pos: Int) {

                val intent = Intent(mContext, com.example.daniel.fitgo.activities.UsserRutinas.RutinaFitGo::class.java)
                intent.putExtra("nombreR", items[position].nombre)
                intent.putExtra("tipoR", items[position].tipo)
                intent.putExtra("descripcionR", items[position].descripcion)
                intent.putExtra("ejerciciosR", items[position].ejercicios)
                intent.putExtra("duracionR", items[position].duracion)
                intent.putExtra("imagenR", items[position].urlimagen)
                intent.putExtra("entrenadorR", items[position].entrenador)

                mContext.startActivity(intent)

            }
        })

    }
    class ViewHolder1(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var costomItemClickListener: CostomItemClickListener?=null


        fun bind(rutina: RutinaFitGo) = with(itemView){

            txtInstructor.text = rutina.entrenador
            txtDescripcion.text = rutina.descripcion
            txtTipo.text=rutina.tipo
            val url: String = rutina.urlimagen
            Picasso.with(context).load(url).fit().into(ImageCard)

            itemView.setOnClickListener(this@ViewHolder1)



        }
        fun setOnClickListener(itemClickListener: CostomItemClickListener){
            this.costomItemClickListener= itemClickListener
        }

        override fun onClick(v: View?) {
            this.costomItemClickListener!!.onCostomItemClickListener(v!!,adapterPosition)
        }
    }
}
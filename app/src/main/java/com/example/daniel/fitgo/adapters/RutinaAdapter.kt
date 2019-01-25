package com.example.daniel.fitgo.adapters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.daniel.fitgo.*
import com.example.daniel.fitgo.activities.UsserRutinas.MyRutina
import com.example.daniel.fitgo.models.Rutina
import kotlinx.android.synthetic.main.fragment_add_rutina.view.*



class RutinaAdapter(val items: List<Rutina>, val userId: String, context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener{

    private lateinit var listener: View.OnClickListener
    var mContext = context
    private val MiRutinas=1
    private val other=2
    private val layoutRut = R.layout.fragment_add_rutina
    override fun onClick(v: View?) {

    }


    override fun getItemViewType(position: Int) = if(items[position].authorId== userId) MiRutinas else other

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            return ViewHolder(parent.inflate(layoutRut))
    }

    override fun getItemCount()=items.size



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position])
        holder.setOnClickListener(object :CostomItemClickListener{
            override fun onCostomItemClickListener(view: View, pos: Int) {

                val intent = Intent(mContext,MyRutina::class.java)
                intent.putExtra("nombre", items[position].nombre)
                intent.putExtra("tipo", items[position].tipo)
                intent.putExtra("descripcion", items[position].descripcion)
                intent.putExtra("ejercicios", items[position].ejercicios)

                mContext.startActivity(intent)

            }
        })

    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var costomItemClickListener: CostomItemClickListener?=null


         fun bind(rutina: Rutina) = with(itemView){
            textNombre.text= rutina.nombre
            textDescripcion.text=rutina.descripcion
            textTipo.text=rutina.tipo

            itemView.setOnClickListener(this@ViewHolder)


        }
        fun setOnClickListener(itemClickListener: CostomItemClickListener){
            this.costomItemClickListener= itemClickListener
        }

        override fun onClick(v: View?) {
            this.costomItemClickListener!!.onCostomItemClickListener(v!!,adapterPosition)
        }
    }

}
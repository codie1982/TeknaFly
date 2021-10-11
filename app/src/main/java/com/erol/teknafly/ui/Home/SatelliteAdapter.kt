package com.erol.teknafly.ui.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.erol.teknafly.R
import com.erol.teknafly.data.di.SatelliteListener
import com.erol.teknafly.data.model.Satellite
import com.erol.teknafly.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.recycler_item.view.*

class SatelliteAdapter( var satelliteListener:SatelliteListener) :
    RecyclerView.Adapter<SatelliteAdapter.SatelliteViewHolder>() {
    val ACTIVE = "active"
    val PASSIVE = "passive"
    lateinit var context: Context
    lateinit var satelliteList: ArrayList<Satellite>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteViewHolder {
        context = parent.context
        var inflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.recycler_item, parent, false)
        //var view = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,R.layout.recycler_item,parent,false)
        return SatelliteViewHolder(view)
    }

    override fun onBindViewHolder(holder: SatelliteViewHolder, position: Int) {

         if(satelliteList !=null){
            if(!satelliteList.isEmpty()){
                var nSatellite = satelliteList[position]
                holder.itemView.txtStarShipName.text = nSatellite.name
                var activeText = ""
                if (nSatellite.active){
                    holder.itemView.txtStarShipStatusPoint.background= context.getDrawable(R.drawable.list_item_status_point_active)
                }else{
                    holder.itemView.txtStarShipStatusPoint.background= context.getDrawable(R.drawable.list_item_status_point_passive)
                }
                if (nSatellite.active) activeText = ACTIVE else activeText = PASSIVE
                holder.itemView.txtStarShipStatusText.text = activeText
                holder.itemView.setOnClickListener {
                    if(nSatellite.active){
                        satelliteListener.onSatelliteItemClick(nSatellite.id)
                    }else{
                        Toast.makeText(context,"Pasif bir Gemi Erişilemez!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    fun setData(satelliteList: ArrayList<Satellite>) {
        this.satelliteList = satelliteList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return satelliteList.size
    }

    class SatelliteViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    }
}
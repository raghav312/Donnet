package com.example.donnet.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donnet.R
import com.example.donnet.activities.GetAddressFromLatLng
import com.example.donnet.models.donnor
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class DonorsAdapter(options: FirestoreRecyclerOptions<donnor>)
    :FirestoreRecyclerAdapter<donnor, DonorsAdapter.DonorViewHolder>(
    options
) {

    private lateinit var c:Context
    class DonorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val blood: TextView = itemView.findViewById(R.id.donBlood)
        val age: TextView = itemView.findViewById(R.id.donAge)
        val name: TextView = itemView.findViewById(R.id.donName)
        val phone: TextView = itemView.findViewById(R.id.donPhone)
        val loc: TextView = itemView.findViewById(R.id.donLoc)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorViewHolder {
        val viewHolder = DonorViewHolder(LayoutInflater.
        from(parent.context).
        inflate(R.layout.finding_cards, parent, false))
        c = parent.context
        return viewHolder
    }

    override fun onBindViewHolder(holder: DonorViewHolder, position: Int, model: donnor) {
        holder.age.text = model.age.toString()
        holder.blood.text = model.blood_type
        holder.name.text = model.name
        holder.phone.text = model.phone
        // location wala dekhte hain

        val addr = GetAddressFromLatLng(c ,model.spot.latitude , model.spot.longitude )
        addr.setAddressListener(object: GetAddressFromLatLng.AddressListener{
            override fun onAddressFound(address:String?){
                holder.loc.text = "Location:- " + address
            }

            override fun onError() {
                holder.loc.text=""
            }


        })

        addr.getAddress()



    }







}


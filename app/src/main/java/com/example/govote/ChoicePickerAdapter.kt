package com.example.govote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_selection_card.view.*

class ChoicePickerAdapter(val context : Context,
                          private val list: ArrayList<PartyPeopleModel>):
        RecyclerView.Adapter<ChoicePickerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvCandidatesName: TextView  = itemView.rvCandidatesName
        val rvCandidatesParty: TextView = itemView.rvCandidatesParty
        val rvCandidatesArea: TextView  = itemView.rvCandidatesArea
        val rvCandidatesId: TextView  = itemView.rvCandidatesId
        val ivCandidatePhoto: ImageView = itemView.ivCandidatePhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater
                .from(context)
                .inflate(R.layout.item_selection_card , parent , false)

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val a:String = "Id: " + list[position].getId().toString()
        holder.rvCandidatesId.text = a

        val b  = "Name: "+ list[position].getName().toString()
        holder.rvCandidatesName.text =b

        val c = "Party: " + list[position].getParty().toString()
        holder.rvCandidatesParty.text = c

        val d = "Area: "+ list[position].getArea().toString()
        holder.rvCandidatesArea.text = d

        holder.ivCandidatePhoto.setImageResource(list[position].getImg())

    }

    override fun getItemCount(): Int {
        return list.size
    }


}
package com.example.govote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.android.synthetic.main.item_selection_card.view.*

class ChoicePickerAdapter(
    private val context : Context,
    private val list: ArrayList<PartyPeopleModel>)
    :RecyclerView.Adapter<ChoicePickerAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        val rvCandidatesName: TextView  = itemView.rvCandidatesName
        val rvCandidatesParty: TextView = itemView.rvCandidatesParty
        val rvCandidatesArea: TextView  = itemView.rvCandidatesArea
        val rvCandidatesId: TextView  = itemView.rvCandidatesId
        val ivCandidatePhoto: ImageView = itemView.ivCandidatePhoto
        val llSelectedHolder: LinearLayout = itemView.llSelectedHolder



    }
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater
                .from(context)
                .inflate(R.layout.item_selection_card , parent , false ))


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

        if(!list[position].getIsSelected()){
            holder.llSelectedHolder.setBackgroundResource(R.drawable.selection_card_outer)
        }else{
            holder.llSelectedHolder.setBackgroundResource(R.drawable.selected_card_outer)
        }

        holder.itemView.setOnClickListener{
            if(onClickListener!= null){
                onClickListener!!.onClick(position,list[position],holder)
            }
        }

        holder


    }

    fun setOnClickListener(onClickListener: OnClickListener?){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(i: Int, model: PartyPeopleModel, holder: ViewHolder)
    }


    override fun getItemCount(): Int {
        return list.size
    }
}
package com.example.govote

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.govote.databinding.ActivityVotePageBinding
import kotlinx.android.synthetic.main.activity_vote_page.*

class VotePage : AppCompatActivity()  {

    private lateinit var binding: ActivityVotePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityVotePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        val list :ArrayList<PartyPeopleModel> = PartyPeople.defaultPPList()
        setData(list)



    }


    private fun setData( list :ArrayList<PartyPeopleModel>){
        for(e:PartyPeopleModel in list){
            e.setIsSelected(false)
        }
        binding.rvCandidates.layoutManager = LinearLayoutManager(this)
        val mainAdapter = ChoicePickerAdapter(this,list)
        binding.rvCandidates.adapter = mainAdapter

        mainAdapter.setOnClickListener(object : ChoicePickerAdapter.OnClickListener{
            override fun onClick(
                i: Int,
                model: PartyPeopleModel,
                holder: ChoicePickerAdapter.ViewHolder
            ) {
                for(p in 0 until list.size){
                    if(i == p){
                        list[p].setIsSelected(true)
                        Toast.makeText(this@VotePage,"LAST UPDATED IS ${list[i].getName()}",Toast.LENGTH_SHORT).show()
                    }else{
                        list[p].setIsSelected(false)
                    }
                    mainAdapter.notifyItemChanged(p)

                }

            }
        })


    }





}
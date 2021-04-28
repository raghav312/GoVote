package com.example.govote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.govote.databinding.ActivityMainBinding
import com.example.govote.databinding.ActivityVotePageBinding
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_vote_page.*

class VotePage : AppCompatActivity() {

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

        binding.rvCandidates.layoutManager = LinearLayoutManager(this)
        val mainAdapter = ChoicePickerAdapter(this,list)
        binding.rvCandidates.adapter = mainAdapter

    }




}
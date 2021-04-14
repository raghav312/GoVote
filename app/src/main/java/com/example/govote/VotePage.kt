package com.example.govote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_vote_page.*

class VotePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_page)

    val parties=Party.getParty()
        val PartyAdapter=PartyAdapter(parties)

        rvCandidates.layoutManager=LinearLayoutManager(this)
        rvCandidates.adapter=PartyAdapter


    }
}
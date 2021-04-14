package com.example.govote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_addhaar_voter_authenticator.*
import kotlin.math.log

class AddhaarVoterAuthenticator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhaar_voter_authenticator)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        VerifyText.setOnClickListener {
            val intent = Intent(this,VotePage::class.java)
            startActivity(intent)
        }






    }
}
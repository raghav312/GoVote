package com.example.govote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

class AddhaarVoterAuthenticator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhaar_voter_authenticator)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }
}
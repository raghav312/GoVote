package com.example.govote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.govote.databinding.ActivityAddhaarVoterAuthenticatorBinding
import kotlinx.android.synthetic.main.activity_addhaar_voter_authenticator.*


class AddhaarVoterAuthenticator : AppCompatActivity() {
    private lateinit var binding: ActivityAddhaarVoterAuthenticatorBinding
    private var isItVerified:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddhaarVoterAuthenticatorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        binding.tvIncorrectCred.visibility = View.GONE
        binding.tvCorrectCred.visibility = View.GONE

        var userAadhaar: String = binding.tinAddharNumber.text.toString()
        verhoeffVerification(userAadhaar)

        binding.btnVerify.setOnClickListener {
            getVerified()
        }

    }


    private fun getVerified(){
        if(isItVerified){
            binding.tinAddharNumber.text?.clear()
            binding.tinVoterId.text?.clear()
            binding.tvIncorrectCred.visibility = View.GONE
            binding.tvCorrectCred.visibility = View.VISIBLE

        }else{
            binding.tvCorrectCred.visibility = View.GONE
            binding.tvIncorrectCred.visibility = View.VISIBLE
        }

    }

    private fun verhoeffVerification(num: String){
        isItVerified = Verhoeff.isAadhaarNumberValid(num)
    }




}
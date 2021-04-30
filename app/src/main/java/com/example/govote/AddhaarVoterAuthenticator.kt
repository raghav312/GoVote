package com.example.govote

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.govote.databinding.ActivityAddhaarVoterAuthenticatorBinding

class AddhaarVoterAuthenticator : AppCompatActivity() {
    var userAadhaar: String = ""
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


        binding.btnVerify.setOnClickListener {
             userAadhaar = binding.tinAddharNumber.text.toString()
            verhoeffVerification(userAadhaar)
            getVerified()
        }

    }


    private fun getVerified(){

        if(isItVerified){
            binding.tinAddharNumber.text?.clear()
            binding.tinVoterId.text?.clear()
            binding.tvIncorrectCred.visibility = View.GONE
            binding.tvCorrectCred.visibility = View.VISIBLE
            val intent = Intent(this, OtpActivity::class.java)
            intent.putExtra("AadhaarUser",userAadhaar)
            Toast.makeText(this, "adhaaar is $userAadhaar", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()

        }else{
            binding.tvCorrectCred.visibility = View.GONE
            binding.tvIncorrectCred.visibility = View.VISIBLE
        }

    }

    private fun verhoeffVerification(num: String){
        isItVerified = AadhaarUtil.isAadhaarNumberValid(num)
    }

}


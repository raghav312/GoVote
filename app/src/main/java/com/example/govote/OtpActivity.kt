package com.example.govote

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.govote.databinding.ActivityOtpBinding
import com.example.govote.doas.UserDao
import com.example.govote.models.User
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        auth = FirebaseAuth.getInstance()
        val login = binding.btnSendOTP
        val verify = binding.btnOTP
        val currentUser = auth.currentUser
        if(currentUser  != null){
            Log.i("asd", " Ptani ye kya ha ")
        }
        login.setOnClickListener {
            getLogIn()
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential?) {

            }

            override fun onVerificationFailed(p0: FirebaseException?) {
                Toast.makeText(this@OtpActivity, "Na chala bhai ", Toast.LENGTH_SHORT).show()
                if (p0 is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this@OtpActivity, "Na chala bhai invalid ", Toast.LENGTH_SHORT).show()


                } else if (p0 is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(this@OtpActivity, "Na chala bhai exceeded ", Toast.LENGTH_SHORT).show()
                }



            }

            override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
                Log.i("TAG", "onCodeSent:$p0")
                if (p0 != null && p1!=null) {
                    storedVerificationId = p0
                    resendToken =p1
                }

            }

        }

        verify.setOnClickListener {
            val code = binding.tinOTP.text.toString().trim()
            if(TextUtils.isEmpty(code)){
                Toast.makeText(
                    this@OtpActivity,
                    "Can not leave this field empty ",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                getUserVerification(storedVerificationId, code)
            }
        }


    }



    private fun getLogIn(){
        val mobileNumber = binding.tinPhoneNumber
        var number = mobileNumber.text.toString().trim()

        if(!number.isEmpty()){
            number = "+91"+number
            sendVerificationCode(number)
        }else{
            Toast.makeText(this@OtpActivity, "Try phone Number again!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun sendVerificationCode(number: String){
        val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun getUserVerification(verificationId: String?, code: String){
        val cred = PhoneAuthProvider.getCredential(verificationId, code)
        signIN(cred)
    }

    private fun signIN(cred: PhoneAuthCredential) {
        auth.signInWithCredential(cred)
                .addOnSuccessListener {

                    val aadhaar=intent.getStringExtra("AadhaarUser")
                    Toast.makeText(this@OtpActivity, "adhaaar is $aadhaar", Toast.LENGTH_SHORT).show()


                    val user= aadhaar?.let { it1 ->
                        User(
                            auth.currentUser.uid, auth.currentUser.phoneNumber,
                            it1
                        )
                    }
                    val usersDao=UserDao()
                    usersDao.addUser(user)

                val phone = auth.currentUser.phoneNumber
                    Toast.makeText(
                        this@OtpActivity,
                        "Success, Logged in as $phone",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@OtpActivity, VotePage::class.java)
                    startActivity(intent)
                    finish()

                }

                .addOnFailureListener {
                    Toast.makeText(this@OtpActivity, "Try OTP again!", Toast.LENGTH_SHORT).show()
                }

    }


}
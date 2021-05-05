package com.example.govote

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.govote.databinding.ActivityOtpBinding
import com.example.govote.doas.UserDao
import com.example.govote.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import java.util.concurrent.TimeUnit


class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    var flag:Boolean?=true
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private val db = FirebaseFirestore.getInstance()
    var voted:Boolean?=false
    var userdb: User?=null
    private lateinit var  view: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        view = binding.root
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
                Snackbar.make(view,"Make Internet Connection!" , 6000).show()

                if (p0 is FirebaseAuthInvalidCredentialsException) {

                    Snackbar.make(view,"Invalid credential" , 6000).show()

                } else if (p0 is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Snackbar.make(view,
                            "Too Many Request, Kindly try again after some time " ,
                            6000).show()

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

                    val aadhaar = intent.getStringExtra("AadhaarUser")
                    val userName = intent.getStringExtra("NameUser")!!
                    val usersDao = UserDao()

                    val usersRef: CollectionReference = db.collection("users")
                    val query: Query = usersRef.whereEqualTo("aadhaarNumber", aadhaar)
                    query.get()
                            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                                if (task.isSuccessful) {
                                    for (documentSnapshot in task.result!!) {
                                        val user = documentSnapshot.getString("aadhaarNumber")
                                       val  voted= documentSnapshot.get("voted") as Boolean
                                        if (user == aadhaar && voted) {
                                            flag=false

                                            Toast.makeText(
                                                    this@OtpActivity,
                                                    "Abe karto diya tha Vote tune",
                                                    Toast.LENGTH_SHORT
                                            ).show()
                                            val intent = Intent(this@OtpActivity, MainActivity::class.java)
                                            startActivity(intent)
                                            finish()

                                        }

                                        else if(user == aadhaar ){
                                                    flag=false
                                            Snackbar.make(view,"Welcome Back" , 3000).show()

                                            val intent = Intent(this@OtpActivity, VotePage::class.java)
                                            intent.putExtra("Aadhaar",aadhaar)
                                            intent.putExtra("un",userName)
                                            startActivity(intent)
                                            finish()

                                        }
                                    }
                                }




                                if (task.result!!.size() === 0 || flag==true) {
                                    val user = aadhaar?.let {it1 ->
                                        User(auth.currentUser.uid, userName, auth.currentUser.phoneNumber, it1)
                                    }

                                    usersDao.addUser(user)

                                    val intent = Intent(this@OtpActivity, VotePage::class.java)
                                    intent.putExtra("Aadhaar",aadhaar)
                                    intent.putExtra("un",userName)
                                    startActivity(intent)
                                    finish()

                                }
                            })
                }

                .addOnFailureListener {
                    Toast.makeText(this@OtpActivity, "Try OTP again!", Toast.LENGTH_SHORT)
                            .show()
                }


    }

}








package com.example.govote

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.govote.databinding.ActivityVotePageBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_vote_page.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class VotePage : AppCompatActivity()  {

    private lateinit var binding: ActivityVotePageBinding
    private lateinit var userName:String
    private lateinit var mainAdapter: ChoicePickerAdapter
    private lateinit var view: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityVotePageBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        // This will put user name at the head
        userName = intent.getStringExtra("un").toString()
        binding.tvUserName.text = binding.tvUserName.text.toString() + userName
        //end

        //Getting the party list and setting it in the adapter
        // using the setData() function
        val list :ArrayList<PartyPeopleModel> = PartyPeople.defaultPPList()
        setData(list)

        // Confirm the selection using alert dailog box
        binding.btnConfirm.setOnClickListener {
            confirmSelection(list)
        }

        binding.btnExitSession.setOnClickListener {
            var f:Boolean = false
            for(i in list){
                if(i.getIsSelected())
                    f = true
                    break
            }

            if(f){
                val intent = Intent(this@VotePage ,MainActivity::class.java )
                startActivity(intent)

            }else{
                val alertDialog  = AlertDialog.Builder(this)
                        .setTitle("You are unfinished !")
                        .setMessage("Want to go without selecting any choice ? ")
                        .setPositiveButton("Yep, idc"){ dialogInterface: DialogInterface, _ ->
                            GlobalScope.launch {
                                itsOver(this@VotePage)
                            }
                            Snackbar.make(binding.root , "BYEE!!" , 6000).show()

                            dialogInterface.dismiss()

                        }
                        .setNegativeButton("Nah, imma select one "){ dialogInterface: DialogInterface, _ ->
                            dialogInterface.dismiss()
                        }

                    alertDialog.create().show()

            }

        }


        binding.btnRefreshOptions.setOnClickListener {
           // Refresh the Adapter
            Snackbar.make(view , "Coming Soon...." , 3000).show()
        }

    }

    suspend fun itsOver(context: Context){
        delay(6000)
        val intent = Intent(context ,MainActivity::class.java )
        startActivity(intent)
    }

    private fun setData( list :ArrayList<PartyPeopleModel>){
        //Initialising all the card as not selected
        for(e:PartyPeopleModel in list){
            e.setIsSelected(false)
        }

        // inflate the card with content
        binding.rvCandidates.layoutManager = LinearLayoutManager(this)
        mainAdapter = ChoicePickerAdapter(this,list)
        binding.rvCandidates.adapter = mainAdapter



        //using the set button function we made on
        //ChoicePicker adapter

        mainAdapter.setOnClickListener(object : ChoicePickerAdapter.OnClickListener{
            override fun onClick(
                    i: Int,
                    model: PartyPeopleModel,
                    holder: ChoicePickerAdapter.ViewHolder
            ) {

                // the latest clicked button will be highlighted and
                // the isSelected will be set from the same choice

                for(p in 0 until list.size){
                    if(i == p){
                        list[p].setIsSelected(true)

                    }else{
                        list[p].setIsSelected(false)
                    }
                    mainAdapter.notifyItemChanged(p)

                }

            }
        })

    }

    private fun confirmSelection(list: ArrayList<PartyPeopleModel>){
        var sel: PartyPeopleModel? = null
        for( i in list){
            if(i.getIsSelected()){
                sel = i
                break
            }
        }

        if(sel != null){
            //Dialog box for confirmation
            showDialogForConfirmation(sel)
        }else{
            showDialogForEmpty()
        }
    }

    private fun showDialogForConfirmation(sel: PartyPeopleModel) {

        val alert = AlertDialog.Builder(this)
                .setTitle("Sure?")
                .setMessage("Your Selected Choice is \n ${sel.getName()}," +
                        "\n id-> ${sel.getId()}." +
                        "\n Do you want to Go with it ? ")
                .setPositiveButton("Yes"){ dialog: DialogInterface ,_->

                    // voted entry is updated in database once the choice is finalized
                    // for the Aaddhar card holder

                    val db = FirebaseFirestore.getInstance()
                    var map =  HashMap<String,Boolean>()
                    map.put("voted",true)

                    val aadhaar = intent.getStringExtra("Aadhaar")

                    db.collection("users")
                            .document(aadhaar!!)
                            .update(map as Map<String, Any>)

                    dialog.dismiss()

                }
                .setNegativeButton("No"){dialog: DialogInterface ,_->
                    dialog.dismiss()
                }

        alert.create().show()
    }


    private fun showDialogForEmpty(){
        val alert = AlertDialog.Builder(this)
                .setTitle("None Selected")
                .setNeutralButton("Ok"){ dialogInterface: DialogInterface, _ ->
                    dialogInterface.dismiss()
                }
        alert.create().show()

    }

}
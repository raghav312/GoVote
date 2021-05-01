package com.example.govote.doas

import com.example.govote.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {

  val db= FirebaseFirestore.getInstance()
    val usersCollection=db.collection("users")

    fun addUser(user: User?){
        user?.let {

            GlobalScope.launch(Dispatchers.IO) {
                usersCollection.document(user.uid).set(it)
            }



        }

    }



}
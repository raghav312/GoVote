package com.example.govote

object PartyPeople {

    fun defaultPPList(): ArrayList<PartyPeopleModel>{

        val list = ArrayList<PartyPeopleModel>()

        val Namo = PartyPeopleModel(1,"Narender Damodardas Modi"  ,R.drawable.modiji,
                "Vellore, Tamil Nadu, 65513 ", "Bhartiya Janta Patry" , false )

        val rahul = PartyPeopleModel(2,"Rahul Gandhi " ,R.drawable.rahulol,
                "Vellore","Indian National Congress", false)

        val yogi = PartyPeopleModel(3,"Yogi Adityanath " , R.drawable.yogi ,
                "Vellore","Samajwadi Party", false)

        list.add(Namo)
        list.add(rahul)
        list.add(yogi)

        return list

    }


}


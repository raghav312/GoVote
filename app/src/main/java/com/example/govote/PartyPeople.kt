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

        val Arvind = PartyPeopleModel(4,"Arvind Kejriwal" , R.drawable.arvindji , "Vellore, Tamil Nadu, 65513 ",
                "Aam Admi Party" , false )

        val sonu  = PartyPeopleModel(5 , "Sonu Sood" , R.drawable.sonu ,
                "Vellore, Tamil Nadu, 65513 ", "One Man Party" , false)

        val raghav  = PartyPeopleModel(6 , "Raghav Sharma" , R.drawable.raghav ,
                "Vellore, Tamil Nadu, 65513 ", "Lets Party" , false)

        list.add(Namo)
        list.add(rahul)
        list.add(yogi)
        list.add(Arvind)
        list.add(sonu)
        list.add(raghav)
        return list

    }


}


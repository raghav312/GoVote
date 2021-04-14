package com.example.govote

data class Party (
        val partyName:String,
        val Candidate:String
        ){

    companion object{

        @JvmField
        val Party_Names= arrayOf("Bharatiya Janata Party","Indian National Congress","Bahujan Samaj Party","Communist Party Of India")
        @JvmField
        val Candidates_Names= arrayOf("Narendra Modi","Rahul Gandhi","Mayawati","Ramachandran")
        @JvmStatic
        fun getParty():ArrayList<Party>{

            val PartyArray=ArrayList<Party>(4)
            for(i in 1..4){
                PartyArray.add(Party(Party_Names[i], Candidates_Names[i]))
            }


            return PartyArray

        }




    }


}
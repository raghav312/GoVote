package com.example.govote.models

class User ( val uid:String="",
             val UserName:String="",
             val phoneNumber:String="",
             val AadhaarNumber:String="",
             val Voted:Boolean=false,
             val choiceId:String = ""
             )
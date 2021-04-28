package com.example.govote

import android.media.Image

class PartyPeopleModel(
        private var id: Int,
        private var name:String,
        private var img: Int,
        private var area:String,
        private var candidateParty: String,
        private var isSelected:Boolean

) {


    fun getId():Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName():String {
        return name
    }

    fun setName(name: String){
        this.name = name
    }

    fun getImg():Int {
        return img
    }

    fun setImg(img: Int){
        this.img = img
    }

    fun setIsSelected():Boolean{
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }

    fun getParty():String{
        return candidateParty
    }

    fun setParty(cp:String){
        this.candidateParty = cp
    }

    fun getArea():String {
        return area
    }

    fun setArea(area: String) {
        this.area = area
    }



}
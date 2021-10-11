package com.erol.teknafly.data.model

import com.google.gson.annotations.SerializedName

data class Satellite(
    @SerializedName("id")
    var id:Int,
    @SerializedName("active")
    var active:Boolean,
    @SerializedName("name")
    var name:String="",
    @SerializedName("cost_per_launch")
    var cost_per_launch:Double=0.0,
    @SerializedName("first_flight")
    var first_flight:String ="",
    @SerializedName("height")
    var height:Int =0,
    @SerializedName("mass")
    var mass:Int=0
) {}
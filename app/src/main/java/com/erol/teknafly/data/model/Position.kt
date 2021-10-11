package com.erol.teknafly.data.model

import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("posX")
    val X:Double,
    @SerializedName("posY")
    val Y:Double) {

}
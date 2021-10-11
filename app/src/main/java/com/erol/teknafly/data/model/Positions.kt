package com.erol.teknafly.data.model

import com.google.gson.annotations.SerializedName

   
data class Positions (

   @SerializedName("posX") var posX : Double,
   @SerializedName("posY") var posY : Double

)
package com.erol.teknafly.data.model

import com.google.gson.annotations.SerializedName


data class STList(

   @SerializedName("id") var id : Int,
   @SerializedName("positions") var positions : List<Positions>

)
package com.erol.teknafly.data.model

import com.google.gson.annotations.SerializedName

   
data class Example(
   @SerializedName("list") var list : List<STList>
)
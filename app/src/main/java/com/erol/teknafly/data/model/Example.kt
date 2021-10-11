package com.erol.teknafly.data.model

import com.google.gson.annotations.SerializedName

   
data class Example<T>(
   @SerializedName("list") var list : STList
)
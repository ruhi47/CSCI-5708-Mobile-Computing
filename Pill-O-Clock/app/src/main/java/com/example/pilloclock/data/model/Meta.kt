package com.example.example

import com.google.gson.annotations.SerializedName


data class Meta (

  @SerializedName("disclaimer"   ) var disclaimer  : String?  = null,
  @SerializedName("terms"        ) var terms       : String?  = null,
  @SerializedName("license"      ) var license     : String?  = null,
  @SerializedName("last_updated" ) var lastUpdated : String?  = null,
  @SerializedName("results"      ) var results     : Results? = Results()

)
package com.example.example

import com.google.gson.annotations.SerializedName


data class FDAMedicationResponse (

  @SerializedName("meta"    ) var meta    : Meta?              = Meta(),
  @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf()

)
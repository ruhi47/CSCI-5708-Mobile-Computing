package com.example.example

import com.google.gson.annotations.SerializedName


data class Results (

  @SerializedName("effective_time"                        ) var effectiveTime                     : String?           = null,
  @SerializedName("inactive_ingredient"                   ) var inactiveIngredient                : ArrayList<String> = arrayListOf(),
  @SerializedName("purpose"                               ) var purpose                           : ArrayList<String> = arrayListOf(),
  @SerializedName("keep_out_of_reach_of_children"         ) var keepOutOfReachOfChildren          : ArrayList<String> = arrayListOf(),
  @SerializedName("warnings"                              ) var warnings                          : ArrayList<String> = arrayListOf(),
  @SerializedName("when_using"                            ) var whenUsing                         : ArrayList<String> = arrayListOf(),
  @SerializedName("questions"                             ) var questions                         : ArrayList<String> = arrayListOf(),
  @SerializedName("spl_product_data_elements"             ) var splProductDataElements            : ArrayList<String> = arrayListOf(),
  @SerializedName("ask_doctor"                            ) var askDoctor                         : ArrayList<String> = arrayListOf(),
  @SerializedName("openfda"                               ) var openfda                           : Openfda?          = Openfda(),
  @SerializedName("version"                               ) var version                           : String?           = null,
  @SerializedName("dosage_and_administration"             ) var dosageAndAdministration           : ArrayList<String> = arrayListOf(),
  @SerializedName("pregnancy_or_breast_feeding"           ) var pregnancyOrBreastFeeding          : ArrayList<String> = arrayListOf(),
  @SerializedName("stop_use"                              ) var stopUse                           : ArrayList<String> = arrayListOf(),
  @SerializedName("storage_and_handling"                  ) var storageAndHandling                : ArrayList<String> = arrayListOf(),
  @SerializedName("do_not_use"                            ) var doNotUse                          : ArrayList<String> = arrayListOf(),
  @SerializedName("package_label_principal_display_panel" ) var packageLabelPrincipalDisplayPanel : ArrayList<String> = arrayListOf(),
  @SerializedName("indications_and_usage"                 ) var indicationsAndUsage               : ArrayList<String> = arrayListOf(),
  @SerializedName("set_id"                                ) var setId                             : String?           = null,
  @SerializedName("id"                                    ) var id                                : String?           = null,
  @SerializedName("ask_doctor_or_pharmacist"              ) var askDoctorOrPharmacist             : ArrayList<String> = arrayListOf(),
  @SerializedName("active_ingredient"                     ) var activeIngredient                  : ArrayList<String> = arrayListOf()

)
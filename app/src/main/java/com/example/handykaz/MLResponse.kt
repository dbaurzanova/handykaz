package com.example.handykaz

import com.google.gson.annotations.SerializedName

data class MLResponse(
    @SerializedName("result")
    val result: String?
)
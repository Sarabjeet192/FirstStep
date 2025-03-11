package com.cgc.firststep.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeTeam {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("code")
    @Expose
    var code: String? = null
}
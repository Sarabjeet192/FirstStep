package com.cgc.firststep.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostDataModel {
    @SerializedName("year")
    @Expose
    var year: Int? = null

    @SerializedName("price")
    @Expose
    var price: Float? = null

    @SerializedName("CPU model")
    @Expose
    var cPUModel: String? = null

    @SerializedName("Hard disk size")
    @Expose
    var hardDiskSize: String? = null
}
package com.cgc.firststep.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FileUploadResponse {
    @SerializedName("originalname")
    @Expose
    var originalname: String? = null

    @SerializedName("filename")
    @Expose
    var filename: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null
}
package com.cgc.firststep.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.text.MatchResult

class MatchResponse {
    @SerializedName("results")
    @Expose
    var results: MutableList<MatchList> = mutableListOf()

}
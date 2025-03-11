package com.cgc.firststep.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MatchList {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("series_id")
    @Expose
    var seriesId: Int? = null

    @SerializedName("venue")
    @Expose
    var venue: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("result")
    @Expose
    var result: String? = null

    @SerializedName("match_title")
    @Expose
    var matchTitle: String? = null

    @SerializedName("match_subtitle")
    @Expose
    var matchSubtitle: String? = null

    @SerializedName("home")
    @Expose
    var home: HomeTeam? = null

    @SerializedName("away")
    @Expose
    var away: AwayTeam? = null
}
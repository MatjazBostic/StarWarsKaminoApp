package com.mbostic.starwarskaminoapp.planetDetails

import com.google.gson.annotations.SerializedName

class LikeResponse {
    @SerializedName("planet_id")
    var planetId: Int? = null

    @SerializedName("likes ")
    var likesNum: Int? = null
}
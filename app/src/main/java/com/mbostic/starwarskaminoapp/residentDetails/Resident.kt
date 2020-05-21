package com.mbostic.starwarskaminoapp.residentDetails

import com.google.gson.annotations.SerializedName

class Resident {

    var name: String? = null

    var height: Int? = null

    var mass: String? = null

    @SerializedName("hair_color")
    var hairColor: String? = null

    @SerializedName("skin_color")
    var skinColor: String? = null

    @SerializedName("eye_color")
    var eyeColor: String? = null

    @SerializedName("birth_year")
    var birthYear: String? = null

    var gender: String? = null

    @SerializedName("homeworld")
    var homeWorld: String? = null

    var created: String? = null

    var edited: String? = null

    @SerializedName("image_url")
    var imageUrl: String? = null

}
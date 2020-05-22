package com.mbostic.starwarskaminoapp.planetDetails

import com.google.gson.annotations.SerializedName

class Planet {

    var name: String? = null

    @SerializedName("rotation_period")
    var rotationPeriod: Int? = null

    @SerializedName("orbital_period")
    var orbitalPeriod: Int? = null

    var diameter: Int? = null

    var climate: String? = null

    var gravity: String? = null

    var terrain: String? = null

    @SerializedName("surface_water")
    var surfaceWater: Int? = null

    var population: Int? = null

    var residents: ArrayList<String>? = null

    var created: String? = null

    var edited: String? = null

    @SerializedName("image_url")
    var imageUrl: String? = null

    var likes: Int? = null
}
package com.mbostic.starwarskaminoapp.planetDetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
import androidx.preference.PreferenceManager
import com.mbostic.starwarskaminoapp.HelperFunctions
import com.mbostic.starwarskaminoapp.residents.ResidentsActivity
import com.mbostic.starwarskaminoapp.StarWarsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanetDetailsPresenter(private var view: View) {

    companion object {
        const val LOG_TAG = "MainActivityPresenter"

        const val PLANET_IMG_SMALL_HEIGHT_DP = 150
        const val PLANET_IMG_BIG_HEIGHT_DP = 400

        const val IS_LIKED_PREF_KEY = "isLiked"
    }

    private var starWarsAPI: StarWarsAPI = StarWarsAPI.getAPI()

    private var planet: Planet? = null

    private lateinit var prefs: SharedPreferences

    /** Fetches the planet info */
    init {
        starWarsAPI.getPlanet(HelperFunctions.DEFAULT_PLANET_ID).enqueue(object : Callback<Planet> {
            override fun onResponse(call: Call<Planet>, response: Response<Planet>) {
                if (response.isSuccessful) {
                    planet = response.body()
                    planet?.let { view.setData(it) }

                    if (prefs.getBoolean(IS_LIKED_PREF_KEY, false)) {
                        planet?.likes = planet?.likes?.plus(1)
                        planet?.likes?.let { view.setLikes(it, likedByUser = true, showToast = false) }
                    }

                } else {
                    Log.d(LOG_TAG, "Code: " + response.code() + " Message: " + response.message())
                    retry(call)
                }
            }

            override fun onFailure(call: Call<Planet>, t: Throwable) {
                t.printStackTrace()
                retry(call)
            }

            fun retry(call: Call<Planet>) {
                Handler().postDelayed({
                    call.clone().enqueue(this)
                }, HelperFunctions.CONNECTION_RETRY_DELAY_MS)
            }
        })

        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext())
    }

    /** Interface to the View */
    interface View {
        fun getContext(): Context
        fun setData(planet: Planet)
        fun setPlanetImgHeight(heightDp: Int)
        fun setLikes(num: Int, likedByUser: Boolean, showToast: Boolean)
    }

    /** Starts the residents activity */
    fun startResidentsActivity() {
        val intent = Intent(view.getContext(), ResidentsActivity::class.java)
        intent.putExtra(ResidentsActivity.RESIDENTS_EXTRA_KEY, planet?.residents)
        intent.putExtra(ResidentsActivity.PLANET_NAME_EXTRA_KEY, planet?.name)
        view.getContext().startActivity(intent)
    }

    /** Called when like FAB is clicked. It handles liking and unliking of the planet. */
    fun likeClicked() {
        starWarsAPI.likePlanet(HelperFunctions.DEFAULT_PLANET_ID).enqueue(object : Callback<LikeResponse> {

            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (!response.isSuccessful) {
                    return
                }

                if (prefs.getBoolean(IS_LIKED_PREF_KEY, false)) {
                    planet?.likes = planet?.likes?.minus(1)
                    planet?.likes?.let { view.setLikes(it, likedByUser = false, showToast = true) }
                    prefs.edit().putBoolean(IS_LIKED_PREF_KEY, false).apply()
                } else {
                    planet?.likes = response.body()?.likesNum?.plus(1)
                    planet?.likes?.let { view.setLikes(it, likedByUser = true, showToast = true) }
                    prefs.edit().putBoolean(IS_LIKED_PREF_KEY, true).apply()
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private var bigImage = false

    /** Sets appropriate height of the planet image when it is clicked. */
    fun planetImageClicked() {

        if (bigImage) {
            view.setPlanetImgHeight(PLANET_IMG_SMALL_HEIGHT_DP)
        } else {
            view.setPlanetImgHeight(PLANET_IMG_BIG_HEIGHT_DP)
        }
        bigImage = !bigImage
    }
}
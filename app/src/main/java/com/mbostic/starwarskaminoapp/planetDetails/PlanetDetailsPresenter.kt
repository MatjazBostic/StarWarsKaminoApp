package com.mbostic.starwarskaminoapp.planetDetails

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.mbostic.starwarskaminoapp.HelperFunctions
import com.mbostic.starwarskaminoapp.residents.ResidentsActivity
import com.mbostic.starwarskaminoapp.StarWarsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanetDetailsPresenter(private var view: View){

    companion object {
        const val LOG_TAG = "MainActivityPresenter"

        const val PLANET_IMG_SMALL_HEIGHT_DP = 150
        const val PLANET_IMG_BIG_HEIGHT_DP = 400
    }

    private var starWarsAPI: StarWarsAPI = StarWarsAPI.getAPI()

    private lateinit var planet: Planet

    init {
        starWarsAPI.getPlanet(HelperFunctions.DEFAULT_PLANET_ID).enqueue(object : Callback<Planet> {
            override fun onResponse(call: Call<Planet>, response: Response<Planet>) {
                if (response.isSuccessful) {
                    planet = response.body()
                    view.setData(planet)
                } else {
                    Log.d(LOG_TAG, "Code: " + response.code() + " Message: " + response.message())
                    retry(call)
                }
            }

            override fun onFailure(call: Call<Planet>, t: Throwable) {
                t.printStackTrace()
                retry(call)
            }

            fun retry(call: Call<Planet>){
                Handler().postDelayed({
                    call.clone().enqueue(this)
                }, HelperFunctions.CONNECTION_RETRY_DELAY_MS)
            }
        })
    }

    interface View {
        fun setData(planet: Planet)
        fun setPlanetImgHeight(heightDp: Int)
    }

    fun startResidentsActivity(context: Context){
        val intent = Intent(context, ResidentsActivity::class.java)
        intent.putExtra(ResidentsActivity.RESIDENTS_EXTRA_KEY, planet.residents)
        intent.putExtra(ResidentsActivity.PLANET_NAME_EXTRA_KEY, planet.name)
        context.startActivity(intent)
    }

    fun newLike(){
        starWarsAPI.likePlanet(HelperFunctions.DEFAULT_PLANET_ID).enqueue(object : Callback<LikeResponse>{

            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {

                Log.d("onResponse", response.body()?.likes.toString() + " " + response.body()?.planetId.toString())
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private var bigImage = false

    fun planetImageClicked() {

        if(bigImage){
            view.setPlanetImgHeight(PLANET_IMG_SMALL_HEIGHT_DP)
        } else {
            view.setPlanetImgHeight(PLANET_IMG_BIG_HEIGHT_DP)
        }
        bigImage = !bigImage
    }
}
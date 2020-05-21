package com.mbostic.starwarskaminoapp.residentDetails

import android.os.Handler
import android.util.Log
import com.mbostic.starwarskaminoapp.HelperFunctions
import com.mbostic.starwarskaminoapp.StarWarsAPI
import com.mbostic.starwarskaminoapp.planetDetails.Planet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResidentDetailsPresenter(private var view: View, residentId: Int) {

    companion object {
        const val LOG_TAG = "ResidentDetailsPresent"
    }

    private var starWarsAPI: StarWarsAPI = StarWarsAPI.getAPI()

    init {
        starWarsAPI.getResident(residentId).enqueue(object : Callback<Resident> {
            override fun onResponse(call: Call<Resident>, response: Response<Resident>) {
                if (response.isSuccessful) {
                    view.setData(response.body())

                    val homeWorldUrl = response.body().homeWorld
                    val homeWorldId = homeWorldUrl?.substring(homeWorldUrl.lastIndexOf("/") + 1, homeWorldUrl.length) ?: return

                    // get homeworld of the resident
                    starWarsAPI.getPlanet(homeWorldId.toInt()).enqueue(object : Callback<Planet> {
                        override fun onResponse(call: Call<Planet>, response: Response<Planet>) {
                            if (response.isSuccessful) {
                                response.body().name?.let { view.setHomeWorldName(it) }
                            } else {
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

                } else {
                    Log.d(LOG_TAG, "Code: " + response.code() + " Message: " + response.message())
                    retry(call)
                }
            }

            override fun onFailure(call: Call<Resident>, t: Throwable) {
                t.printStackTrace()
                retry(call)
            }

            fun retry(call: Call<Resident>) {
                Handler().postDelayed({
                    call.clone().enqueue(this)
                }, HelperFunctions.CONNECTION_RETRY_DELAY_MS)
            }

        })
    }

    interface View {
        fun setData(resident: Resident)
        fun setHomeWorldName(name: String)
    }
}
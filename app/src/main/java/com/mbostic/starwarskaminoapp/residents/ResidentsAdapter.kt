package com.mbostic.starwarskaminoapp.residents

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbostic.starwarskaminoapp.R
import com.mbostic.starwarskaminoapp.residentDetails.Resident
import com.mbostic.starwarskaminoapp.residentDetails.ResidentDetailsActivity
import com.mbostic.starwarskaminoapp.StarWarsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResidentsAdapter(private val context: Context, private val urls: List<String>) : RecyclerView.Adapter<ResidentsAdapter.ResidentViewHolder>() {

    companion object {
        const val LOG_TAG = "ResidentsAdapter"
    }

    private val residentNames = arrayOfNulls<String>(urls.size)
    private val starWarsApi = StarWarsAPI.getAPI()

    inner class ResidentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var residentNameTextView: TextView = itemView.findViewById(R.id.residentNameTV)
        lateinit var residentId: String

        init {
            residentNameTextView.setOnClickListener {
                val intent = Intent(context, ResidentDetailsActivity::class.java)
                intent.putExtra(ResidentDetailsActivity.RESIDENT_ID_EXTRA, residentId.toInt())
                context.startActivity(intent)
            }
        }
    }

    /** Sets up the viewHolder*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResidentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.residents_recycler_view_row, parent, false)
        return ResidentViewHolder(view)
    }

    /** Returns the count of data*/
    override fun getItemCount(): Int {
        return urls.size
    }

    /** Fetches the name of the resident and puts it into the [holder] */
    override fun onBindViewHolder(holder: ResidentViewHolder, position: Int) {
        val url = urls[position]
        holder.residentId = url.substring(url.lastIndexOf("/") + 1, url.length)
        // check if the name was already fetched
        if (residentNames[position] != null) {
            holder.residentNameTextView.text = residentNames[position]
            return
        }
        holder.residentNameTextView.text = ""

        starWarsApi.getResident(holder.residentId.toInt()).enqueue(object : Callback<Resident> {
            override fun onResponse(call: Call<Resident>, response: Response<Resident>) {
                if (response.isSuccessful) {
                    holder.residentNameTextView.text = response.body()?.name
                    residentNames[position] = response.body()?.name
                } else {
                    Log.d(LOG_TAG, "fail - not successful:  " + response.raw())
                }
            }

            override fun onFailure(call: Call<Resident>, t: Throwable) {
                Log.d(LOG_TAG, "fail - onFailure: " + t.message)
                t.printStackTrace()
            }
        })
    }
}
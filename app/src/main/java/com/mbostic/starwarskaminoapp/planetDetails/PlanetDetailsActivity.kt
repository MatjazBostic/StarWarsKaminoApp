package com.mbostic.starwarskaminoapp.planetDetails

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mbostic.starwarskaminoapp.HelperFunctions
import com.mbostic.starwarskaminoapp.R
import kotlinx.android.synthetic.main.activity_planet_details.*

class PlanetDetailsActivity : AppCompatActivity(), PlanetDetailsPresenter.View {

    private lateinit var planetImageView: ImageView

    private lateinit var presenter: PlanetDetailsPresenter

    private var dataSet = false

    private lateinit var likesTextView: TextView

    private lateinit var likeFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet_details)
        setSupportActionBar(toolbar)

        planetImageView = findViewById(R.id.imageView)
        likeFab = findViewById(R.id.likeFab)

        presenter = PlanetDetailsPresenter(this)
    }

    /** Called when planet image is clicked */
    fun onClickPlanetImage(view: View) {
        presenter.planetImageClicked()
    }

    /** Called when like button is clicked */
    fun onClickLikeFab(view: View) {
        if (dataSet) {
            presenter.likeClicked()
        }
    }

    /** Called when residents FAB is clicked. It starts the residents activity */
    fun onClickResidents(view: View) {
        if (dataSet) {
            presenter.startResidentsActivity()
        }
    }

    /** Sets the given [heightDp] to planet image */
    override fun setPlanetImgHeight(heightDp: Int) {
        val heightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDp.toFloat(), // big image height in dp
            resources.displayMetrics)

        planetImageView.updateLayoutParams {
            height = heightPx.toInt()
        }
    }

    /** Sets the new [num] of likes to the likesTextView. If [showToast] is true, it displays a Toast with the amount of likes */
    override fun setLikes(num: Int, likedByUser: Boolean, showToast: Boolean) {
        likesTextView.text = num.toString()
        if (likedByUser) {
            likeFab.setImageResource(R.drawable.ic_thumb_up_white_24dp)
        } else {
            likeFab.setImageResource(R.drawable.ic_thumb_up_outline_white_24px)
        }
        if (showToast) {
            Toast.makeText(this, getString(R.string.new_amount_of_likes) + num, Toast.LENGTH_LONG).show()
        }
    }

    /** Returns the activity context*/
    override fun getContext(): Context {
        return this
    }

    /** Sets the properties of the given [planet] to the UI */
    override fun setData(planet: Planet) {
        planetImageView.let { Glide.with(this).load(HelperFunctions.forceHttpsInUrl(planet.imageUrl)).into(it) }

        val insertPoint = findViewById<ViewGroup>(R.id.parentLayout)

        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.name), planet.name)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.rotation_period), planet.rotationPeriod.toString())
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.orbital_period), planet.orbitalPeriod.toString())
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.diameter), planet.diameter.toString())
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.climate), planet.climate)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.gravity), planet.gravity)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.terrain), planet.terrain)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.surface_water), planet.surfaceWater.toString())
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.population), planet.population.toString())

        val dateCreatedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'", "dd.MM.yyyy", planet.created)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.date_created), dateCreatedStr)

        val timeCreatedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'", "HH:mm:ss", planet.created)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.time_created), timeCreatedStr)

        val dateEditedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'", "dd.MM.yyyy", planet.edited)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.date_edited), dateEditedStr)

        val timeEditedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'", "HH:mm:ss", planet.edited)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.time_edited), timeEditedStr)


        layoutInflater.inflate(R.layout.planet_information_row, null).let {
            likesTextView = it.findViewById(R.id.propertyValue)
            it.findViewById<TextView>(R.id.propertyName).text = getString(R.string.likes) + ':'
            likesTextView.text = planet.likes.toString()
            insertPoint.addView(it)
        }

        findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        dataSet = true
    }
}

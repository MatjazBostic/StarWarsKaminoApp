package com.mbostic.starwarskaminoapp.planetDetails

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.mbostic.starwarskaminoapp.HelperFunctions
import com.mbostic.starwarskaminoapp.R
import kotlinx.android.synthetic.main.activity_planet_details.*

class PlanetDetailsActivity : AppCompatActivity(), PlanetDetailsPresenter.View {

    private lateinit var planetImageView: ImageView

    private lateinit var presenter: PlanetDetailsPresenter

    private var dataSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet_details)
        setSupportActionBar(toolbar)

        planetImageView = findViewById(R.id.imageView)

        presenter = PlanetDetailsPresenter(this)

    }

    fun onClickPlanetImage(view: View) {
        presenter.planetImageClicked()
    }

    fun onClickLikeFab(view: View) {
        if (dataSet) {
            presenter.newLike()
        }
    }

    fun onClickResidents(view: View) {
        if (dataSet) {
            presenter.startResidentsActivity(this)
        }
    }

    override fun setPlanetImgHeight(heightDp: Int) {
        val heightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDp.toFloat(), // big image height in dp
            resources.displayMetrics)

        planetImageView.updateLayoutParams {
            height = heightPx.toInt()
        }
    }

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

        val dateCreatedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'",
            "dd.MM.yyyy", planet.created)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.date_created), dateCreatedStr)

        val timeCreatedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'",
            "HH:mm:ss", planet.created)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.time_created), timeCreatedStr)

        val dateEditedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'",
            "dd.MM.yyyy", planet.edited)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.date_edited), dateEditedStr)

        val timeEditedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'",
            "HH:mm:ss", planet.edited)
        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.time_edited), timeEditedStr)

        HelperFunctions.showProperty(layoutInflater, insertPoint, getString(R.string.likes), planet.likes.toString())

        findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        dataSet = true
    }
}

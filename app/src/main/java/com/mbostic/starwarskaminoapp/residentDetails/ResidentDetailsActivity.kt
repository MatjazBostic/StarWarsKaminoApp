package com.mbostic.starwarskaminoapp.residentDetails

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mbostic.starwarskaminoapp.HelperFunctions
import com.mbostic.starwarskaminoapp.R

import kotlinx.android.synthetic.main.activity_resident_details.*

class ResidentDetailsActivity : AppCompatActivity(), ResidentDetailsPresenter.View {

    companion object {
        const val RESIDENT_ID_EXTRA = "residentId"
    }

    private lateinit var residentImageView: ImageView
    private lateinit var propertyInsertPoint: ViewGroup

    private lateinit var presenter: ResidentDetailsPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_details)
        setSupportActionBar(toolbar)

        residentImageView = findViewById(R.id.imageView)
        propertyInsertPoint = findViewById(R.id.parentLayout)

        presenter = ResidentDetailsPresenter(this, intent.getIntExtra(RESIDENT_ID_EXTRA, 0))
    }

    override fun setData(resident: Resident) {

        residentImageView.let {
            Glide.with(this).load(HelperFunctions.forceHttpsInUrl(resident.imageUrl))
                .error(Glide.with(this).load(R.drawable.ic_broken_image_gray_24dp)).into(it)
        }

        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.name), resident.name)
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.height), resident.height.toString())
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.mass), resident.mass.toString())
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.hairColor), resident.hairColor)
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.skinColor), resident.skinColor)
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.eyeColor), resident.eyeColor)
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.birthYear), resident.birthYear)
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.gender), resident.gender)

        val dateCreatedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'",
            "dd.MM.yyyy", resident.created)
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.date_created), dateCreatedStr)

        val timeCreatedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'",
            "HH:mm:ss", resident.created)
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.time_created), timeCreatedStr)

        val dateEditedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'",
            "dd.MM.yyyy", resident.edited)
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.date_edited), dateEditedStr)

        val timeEditedStr = HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'",
            "HH:mm:ss", resident.edited)
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.time_edited), timeEditedStr)

        findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE

    }

    override fun setHomeWorldName(name: String) {
        HelperFunctions.showProperty(layoutInflater, propertyInsertPoint, getString(R.string.homeworldName), name)
    }

}

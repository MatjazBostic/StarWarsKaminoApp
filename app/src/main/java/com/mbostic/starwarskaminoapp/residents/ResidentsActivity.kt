package com.mbostic.starwarskaminoapp.residents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbostic.starwarskaminoapp.R
import kotlinx.android.synthetic.main.activity_residents.*

class ResidentsActivity : AppCompatActivity() {

    companion object {
        const val RESIDENTS_EXTRA_KEY = "residents"
        const val PLANET_NAME_EXTRA_KEY = "planetName"
    }

    /** Initializes recyclerView*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_residents)
        setSupportActionBar(toolbar)

        title = getString(R.string.residents_of) + intent.getStringExtra(PLANET_NAME_EXTRA_KEY)

        val urls = intent.getStringArrayListExtra(RESIDENTS_EXTRA_KEY)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ResidentsAdapter(this, urls)

    }

}

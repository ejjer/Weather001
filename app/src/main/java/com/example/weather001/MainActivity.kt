package com.example.weather001

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather001.ui.main.MainFragment
import com.example.weather001.ui.main.details.DetailsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInterface())
                .commitNow()
        }
    }
}
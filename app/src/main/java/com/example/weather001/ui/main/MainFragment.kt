package com.example.weather001.ui.main

import androidx.fragment.app.Fragment
import com.example.weather001.ui.main.model.repository.entities.Weather

class MainFragment : Fragment() {
    interface OnItemViewClickListener{
        fun onItemViewClick(weather: Weather)
    }
}
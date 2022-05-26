package com.example.weather001.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather001.ui.main.details.DetailsFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.weather001.databinding.MainFragmentRecyclerItemBinding
import com.example.weather001.ui.main.model.repository.entities.Weather

//создаем адаптер
class MainFragmentAdapter(private val itemClickListener: DetailsFragment.OnItemViewClickListener)
    : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var weatherData: List<Weather> = listOf()
    private lateinit var binding: MainFragmentRecyclerItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = MainFragmentRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount() = weatherData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) = with(binding) {
            mainFragmentRecyclerItemTextView.text = weather.city.city
            root.setOnClickListener { itemClickListener.onItemViewClick(weather) }
        }
    }
}
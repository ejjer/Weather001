package com.example.weather001.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.weather001.R
import com.example.weather001.databinding.DetailsFragmentBinding
import com.example.weather001.ui.main.model.repository.AppState
import com.example.weather001.ui.main.model.repository.entities.Weather
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment : Fragment() {
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let {
            renderStaticData(it)
            viewModel.weatherLiveData.observe(viewLifecycleOwner) { appState -> renderDynamicData(appState)}
            viewModel.loadData(it.city.lat, it.city.lon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderStaticData(weather: Weather) = with(binding) {
        val city = weather.city
        cityName.text = city.city
        cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            city.lat.toString(),
            city.lon.toString()
        )
    }

    private fun renderDynamicData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Error -> {
                mainView.visibility = View.INVISIBLE
                progressBar.visibility = View.GONE
                errorTV.visibility = View.VISIBLE
            }
            AppState.Loading -> {
                mainView.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                progressBar.visibility = View.GONE
                mainView.visibility = View.VISIBLE
                temperatureValue.text = appState.weatherData[0].temperature.toString()
                feelsLikeValue.text = appState.weatherData[0].feelsLike.toString()
                weatherCondition.text = appState.weatherData[0].condition

                imageView.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                
            }
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
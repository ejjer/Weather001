package com.example.weather001.ui.main.theads

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weather001.databinding.FragmentThreadsBinding
import com.example.weather001.services.ForegroundService
import com.example.weather001.services.UsualService
import java.util.*

class ThreadsFragment : Fragment() {
    private var _binding: FragmentThreadsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThreadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        startServiceBtn.setOnClickListener { UsualService.start(requireContext()) }
        button.setOnClickListener {
            val handler = Handler(Looper.getMainLooper())
            Thread {
                val result = startCalculations(editText.text.toString().toInt())
                handler.post { textView.text = result }
                /*handler.postDelayed({
                    textView.text = result
                }, 1000)*/
                // textView.post { textView.text = result }
                /*activity?.runOnUiThread {
                    textView.text = result
                }*/
            }.start()

            /*val handlerThread = HandlerThread(getString(R.string.my_handler_thread))
            handlerThread.start()
            val handlerNew = Handler(handlerThread.looper)
            handlerNew.post { // наш код
            }
            handlerThread.quitSafely()*/
        }
        ForegroundService.start(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun startCalculations(seconds: Int): String {
        val date = Date()
        var diffInSec: Long
        do {
            val currentDate = Date()
            val diffInMs: Long = currentDate.time - date.time
            diffInSec = diffInMs / 1000L
        } while (diffInSec < seconds)
        return diffInSec.toString()
    }

    companion object {
        fun newInstance() = ThreadsFragment()
    }
}
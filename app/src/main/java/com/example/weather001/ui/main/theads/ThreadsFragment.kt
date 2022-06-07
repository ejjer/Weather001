package com.example.weather001.ui.main.theads

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weather001.databinding.FragmentThreadsBinding
import com.example.weather001.services.BoundService
import com.example.weather001.services.ForegroundService
import com.example.weather001.services.UsualService
import java.util.*

class ThreadsFragment : Fragment() {
    private var _binding: FragmentThreadsBinding? = null
    private val binding get() = _binding!!

    private var isBound = false
    private var boundService: BoundService.ServiceBinder? = null

    private val testReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(
                context,
                "FROM SERVICE: ${intent?.getBooleanExtra(ForegroundService.INTENT_SERVICE_DATA, false)}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private val boundServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            boundService = service as BoundService.ServiceBinder
            isBound = boundService != null
            Log.i("SERVICE", "BOUND SERVICE")
            Log.i("SERVICE", "next fibonacci: ${service.nextFibonacci}")
        }


        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
            boundService = null
        }
    }

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
            }.start()
        }
        ForegroundService.start(requireContext())
    }

    override fun onStart() {
        super.onStart()
        if (!isBound) {
            val bindServiceIntent = Intent(context, BoundService::class.java)
            activity?.bindService(bindServiceIntent, boundServiceConnection, Context.BIND_AUTO_CREATE)
        }
        activity?.registerReceiver(testReceiver, IntentFilter(ForegroundService.INTENT_ACTION_KEY))

    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            activity?.unbindService(boundServiceConnection)
        }
        activity?.unregisterReceiver(testReceiver)

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
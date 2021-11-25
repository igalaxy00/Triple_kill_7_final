package com.example.triple_kill_7_2
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.triple_kill_7_2.databinding.SecondFragmentBinding

class SecondFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SecondFragmentBinding.inflate(layoutInflater)
        val navController = findNavController()

        binding.toFirst.setOnClickListener {
            navController.navigate(R.id.action_second_to_first)
        }

        binding.toThird.setOnClickListener {
            navController.navigate(R.id.action_second_to_third)
        }

        return binding.root
    }
}
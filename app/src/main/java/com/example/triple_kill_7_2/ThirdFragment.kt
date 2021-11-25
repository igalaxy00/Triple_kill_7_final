package com.example.triple_kill_7_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.triple_kill_7_2.databinding.ThirdFragmentBinding

class ThirdFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ThirdFragmentBinding.inflate(layoutInflater)
        val navController = findNavController()

        binding.toFirst.setOnClickListener {
            navController.navigate(R.id.action_third_to_first)
        }

        binding.toSecond.setOnClickListener {
            navController.navigate(R.id.action_third_to_second)
        }

        return binding.root
    }
}
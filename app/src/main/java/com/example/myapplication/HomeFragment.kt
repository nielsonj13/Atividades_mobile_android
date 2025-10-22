package com.example.myapplication


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.buttonViewHabit.setOnClickListener {
            val habitIdToPass = "123" // ID simulado

            // Use a classe Directions gerada pelo Safe Args
            val action = HomeFragmentDirections.actionHomeFragmentToHabitDetailFragment(habitIdToPass)

            // Navegue usando o NavController
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
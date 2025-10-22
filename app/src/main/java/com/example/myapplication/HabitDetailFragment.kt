package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentHabitDetailBinding

class HabitDetailFragment : Fragment(R.layout.fragment_habit_detail) {

    private var _binding: FragmentHabitDetailBinding? = null
    private val binding get() = _binding!!

    // Delegado de propriedade para obter os argumentos de forma segura
    private val args: HabitDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabitDetailBinding.bind(view)

        // Acesse o argumento 'habitId' de forma segura
        val receivedId = args.habitId
        binding.textViewDetail.text = "Exibindo detalhes para o Hábito ID: $receivedId"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
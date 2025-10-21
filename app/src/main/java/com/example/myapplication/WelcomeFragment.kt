package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    // Delegado de propriedade para obter os argumentos de forma segura
    private val args: WelcomeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWelcomeBinding.bind(view)

        // Acesse o argumento 'username'
        val username = args.username

        // Exiba a mensagem de boas-vindas
        binding.textViewWelcome.text = "Bem-vindo(a), $username!"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
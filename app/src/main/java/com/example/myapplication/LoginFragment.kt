package com.example.myapplication


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            if (username.isNotBlank()) {
                // Use a classe Directions gerada pelo Safe Args
                val action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment(username)

                // Navegue usando a ação
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
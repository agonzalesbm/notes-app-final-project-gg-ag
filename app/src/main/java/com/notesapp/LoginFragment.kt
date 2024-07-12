package com.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.notesapp.databinding.FragmentLoginBinding
import com.notesapp.viewmodels.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var binding: FragmentLoginBinding
    lateinit var loginViewModel: LoginViewModel
    private val failLogin = "Login failed. Please try again."

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = (activity as MainActivity).notesLoginViewModel
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this
        lifecycleScope.launch {
            if (loginViewModel.isSessionCreated()) {
                binding.root.visibility = View.GONE
                lifecycleScope.launch {
                    delay(100)
                    binding.root.findNavController()
                        .navigate(R.id.action_loginFragment_to_homeFragment2)
                }
                return@launch
            }
        }
        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                val result = loginViewModel.login()
                if (result == null) {
                    Toast.makeText(context, failLogin, Toast.LENGTH_SHORT).show()
                    return@launch
                } else {
                    binding.entryEmail.setText("")
                    binding.entryPassword.setText("")
                    binding.root.findNavController()
                        .navigate(R.id.action_loginFragment_to_homeFragment2)
                }
            }
        }
    }
}
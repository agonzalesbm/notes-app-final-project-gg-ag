package com.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.notesapp.databinding.FragmentLoginBinding
import com.notesapp.viewmodels.LoginViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var binding: FragmentLoginBinding
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = (activity as MainActivity).notesLoginViewModel
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this
        binding.loginButton.setOnClickListener {
            loginViewModel.login()
            binding.root.findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
        }
    }
}
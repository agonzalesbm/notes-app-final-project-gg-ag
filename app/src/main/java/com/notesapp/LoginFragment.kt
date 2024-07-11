package com.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.notesapp.databinding.FragmentLoginBinding
import com.notesapp.models.User
import com.notesapp.viewmodels.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.notesapp.datastore.user

class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var binding: FragmentLoginBinding
    lateinit var loginViewModel: LoginViewModel
    lateinit var user: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        user = requireContext().user
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = (activity as MainActivity).notesLoginViewModel
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this
        binding.loginButton.setOnClickListener {
            println("clicked")
            lifecycleScope.launch(Dispatchers.IO) {
//                saveValues(
//                    loginViewModel.username.value.toString(),
//                    loginViewModel.password.value.toString()
//                )
//                getUserProfile()
                var response = loginViewModel.login()
            }
            binding.root.findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
        }
    }

    private suspend fun saveValues(email: String, password: String) {
        user.edit { preferences ->
            preferences[stringPreferencesKey("email")] = email
            preferences[stringPreferencesKey(("password"))] = password
        }
    }

    private suspend fun getUserProfile() = user.data.collect { user ->
        val name = user[stringPreferencesKey("email")] ?: "THE_NAME"
        val email = user[stringPreferencesKey(("password"))] ?: "EMAIL"
        println(name)
        println(email)
    }


}
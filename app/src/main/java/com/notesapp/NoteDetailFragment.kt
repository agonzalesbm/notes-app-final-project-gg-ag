package com.notesapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.notesapp.databinding.FragmentNoteDetailBinding
import com.notesapp.network.ConnectivityObserver
import com.notesapp.network.NetworkConnectivityObserver
import com.notesapp.viewmodels.NoteDetailsViewModel
import com.notesapp.viewmodels.NotesSharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {
    lateinit var binding: FragmentNoteDetailBinding
    lateinit var viewModel: NotesSharedViewModel
    lateinit var detailsViewModel: NoteDetailsViewModel
    lateinit var locationProvider: FusedLocationProviderClient
    private val permissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                tryGetLastLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                tryGetLastLocation()
            }
            else -> {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).notesViewModel
        detailsViewModel = (activity as MainActivity).notesDetailsViewModel
        binding.viewModel = viewModel
        binding.detailsViewModel = detailsViewModel
        binding.lifecycleOwner = this
        detailsViewModel.updateTexts()
        locationProvider = LocationServices.getFusedLocationProviderClient(requireContext())
        binding.backButton.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_noteDetailFragment_to_homeFragment)
        }
        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                detailsViewModel.save()
                tryGetLastLocation()
            }
        }
        binding.locationButton.setOnClickListener {
            val location = detailsViewModel.getLocation()
            println(location)
            val gmmIntentUri = Uri.parse("geo:0,0?q=$location")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun tryGetLastLocation() {
        val ownerContext = requireContext()
        val hasFineLocation = ActivityCompat.checkSelfPermission(ownerContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ActivityCompat.checkSelfPermission(ownerContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (!hasCoarseLocation && !hasFineLocation) {
            permissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            return
        }
        locationProvider.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                detailsViewModel.latitude.value = location.latitude.toFloat()
                detailsViewModel.longitude.value = location.longitude.toFloat()
            }
        }
        binding.root.findNavController().navigate(R.id.action_noteDetailFragment_to_homeFragment)
    }
}
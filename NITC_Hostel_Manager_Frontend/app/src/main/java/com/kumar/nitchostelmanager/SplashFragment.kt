package com.kumar.nitchostelmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kumar.nitchostelmanager.databinding.FragmentSplashBinding
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment:Fragment() {
    private val profileViewModel:ProfileViewModel by activityViewModels()
    private lateinit var binding:FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater,container,false)

        var loggedIn = LocalStorageAccess(
            this@SplashFragment,
            requireContext(),
            profileViewModel
        ).getData()
        var splashCoroutineScope = CoroutineScope(Dispatchers.Main)
        splashCoroutineScope.launch {
            delay(1000)
            if(loggedIn){
                when(profileViewModel.userType){
                    "Admin"->findNavController().navigate(R.id.adminDashboardFragment)
                    "Warden"->findNavController().navigate(R.id.wardenDashboardFragment)
                    "Student"->findNavController().navigate(R.id.studentDashboardFragment)
                    else->findNavController().navigate(R.id.loginFragment)
                }
            }else{
                findNavController().navigate(R.id.loginFragment)
            }
        }

        return binding.root
    }
}
package com.kumar.nitchostelmanager.viewModel

import androidx.lifecycle.ViewModel
import com.kumar.nitchostelmanager.models.Admin
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.models.Warden

class ProfileViewModel: ViewModel() {
    var userType:String? = null
    var loginToken:String? = null
    var loggedIn:Boolean = false
    lateinit var currentStudent:Student
    lateinit var currentWarden:Warden
    lateinit var currentAdmin:Admin
}
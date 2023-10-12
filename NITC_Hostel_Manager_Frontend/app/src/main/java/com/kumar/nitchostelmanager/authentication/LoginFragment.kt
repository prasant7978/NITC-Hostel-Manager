package com.kumar.nitchostelmanager.authentication

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.authentication.access.LoginAccess
import com.kumar.nitchostelmanager.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var userType: String = "Student"
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.progressBarLogin.visibility = View.INVISIBLE
        binding.adminLoginTypeButton.setOnClickListener {
            binding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            binding.adminLoginTypeButton.setTextColor(Color.WHITE)
            binding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.studentLoginTypeButton.setTextColor(Color.BLACK)
            binding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.contractorLoginTypeButton.setTextColor(Color.BLACK)

            userType = "Admin"
        }

        binding.studentLoginTypeButton.setOnClickListener {
            binding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.adminLoginTypeButton.setTextColor(Color.BLACK)
            binding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            binding.studentLoginTypeButton.setTextColor(Color.WHITE)
            binding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.contractorLoginTypeButton.setTextColor(Color.BLACK)

            userType = "Student"
        }

        binding.contractorLoginTypeButton.setOnClickListener {
            binding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.adminLoginTypeButton.setTextColor(Color.BLACK)
            binding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.studentLoginTypeButton.setTextColor(Color.BLACK)
            binding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            binding.contractorLoginTypeButton.setTextColor(Color.WHITE)

            userType = "Warden"
        }

        binding.buttonSignin.setOnClickListener {
            Log.d("emailEntered","Email = ${binding.editTextLoginEmail.text.toString()} and password = ${binding.editTextLoginPassword.text.toString()}")
            val userEmail = binding.editTextLoginEmail.text.toString()
            val userPassword = binding.editTextLoginPassword.text.toString()

            if(userEmail.isEmpty() || userPassword.isEmpty()){
                Toast.makeText(context, "Please enter both email $userEmail and password = $userPassword to login.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                login(userEmail, userPassword, userType)
            }
        }

        binding.textViewForgotPassword.setOnClickListener {
//            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
//            startActivity(intent)
        }

        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)
        return binding.root
    }

    private fun login(username: String, password: String, userType: String) {
        binding.buttonSignin.isClickable = false
        binding.progressBarLogin.visibility = View.VISIBLE

        when (userType) {
            "Student" -> {
                if (!checkConstraints(username)) {
                    Toast.makeText(context, "Enter a valid nitc roll Number", Toast.LENGTH_SHORT)
                        .show()
                    binding.buttonSignin.isClickable = true
                    binding.progressBarLogin.visibility = View.INVISIBLE
                    return
                }

            }
            "" -> {
                binding.buttonSignin.isClickable = true
                binding.progressBarLogin.visibility = View.INVISIBLE
                Toast.makeText(context, "Select a user type", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val loginCoroutineScope = CoroutineScope(Dispatchers.Main)
        loginCoroutineScope.launch {
            val loggedIn = LoginAccess(requireContext(),this@LoginFragment,profileViewModel).login(
                username,
                password,
                userType
            )

            loginCoroutineScope.cancel()

            if(loggedIn){
                when(userType){
                    "Student" ->findNavController().navigate(R.id.studentDashboardFragment)
                    "Admin"->findNavController().navigate(R.id.adminDashboardFragment)
                    else->{
                        findNavController().navigate(R.id.wardenDashboardFragment)
                    }
                }

            }
            else{
                Toast.makeText(context,"Wrong credentials, please try again.",Toast.LENGTH_SHORT).show()
            }
        }

        binding.progressBarLogin.visibility = View.INVISIBLE
        binding.buttonSignin.isClickable = true
    }

    private fun checkConstraints(roll: String): Boolean {
//        if(email.contains('_')) {
//            val roll = email.substring(email.indexOf("_") + 1, email.length)
            if (roll[0] == 'm' || roll[0] == 'b' || roll[0] == 'p') {
//                if(roll.contains('@')) {
//                    val domain = roll.substring(roll.indexOf("@") + 1, roll.length)
//                    return domain == "nitc.ac.in"
//                }
//                else{
//                    return false
//                }
                return roll.length == 9
            } else {
                return false
            }
//        }
//        else{
//            return false
//        }
    }
}
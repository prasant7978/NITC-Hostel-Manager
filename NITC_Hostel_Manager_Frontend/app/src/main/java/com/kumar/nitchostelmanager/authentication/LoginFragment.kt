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
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.Validation
import com.kumar.nitchostelmanager.authentication.access.LoginAccess
import com.kumar.nitchostelmanager.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LoginFragment : Fragment(), CircleLoadingDialog, Validation {
    private lateinit var binding: FragmentLoginBinding
    private var userType: String = "Student"
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.adminLoginTypeButton.setOnClickListener {
            binding.usernameInputLayoutInLoginFragment.setHint("Username")
            binding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            binding.adminLoginTypeButton.setTextColor(Color.WHITE)
            binding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.studentLoginTypeButton.setTextColor(Color.BLACK)
            binding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.contractorLoginTypeButton.setTextColor(Color.BLACK)

            userType = "Admin"
        }

        binding.studentLoginTypeButton.setOnClickListener {
            binding.usernameInputLayoutInLoginFragment.setHint("Roll No")
            binding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.adminLoginTypeButton.setTextColor(Color.BLACK)
            binding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            binding.studentLoginTypeButton.setTextColor(Color.WHITE)
            binding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.contractorLoginTypeButton.setTextColor(Color.BLACK)

            userType = "Student"
        }

        binding.contractorLoginTypeButton.setOnClickListener {
            binding.usernameInputLayoutInLoginFragment.setHint("Username")
            binding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.adminLoginTypeButton.setTextColor(Color.BLACK)
            binding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            binding.studentLoginTypeButton.setTextColor(Color.BLACK)
            binding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            binding.contractorLoginTypeButton.setTextColor(Color.WHITE)

            userType = "Warden"
        }

        binding.buttonSignin.setOnClickListener {
//            Log.d("emailEntered","Email = ${binding.editTextLoginEmail.text.toString()} and password = ${binding.editTextLoginPassword.text.toString()}")

            val userRollNo = binding.editTextLoginEmail.text.toString()
            val userPassword = binding.editTextLoginPassword.text.toString()

            if(userRollNo.isEmpty() || userPassword.isEmpty()){
                Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                login(userRollNo, userPassword, userType)
            }
        }

//        binding.textViewForgotPassword.setOnClickListener {
//            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
//            startActivity(intent)
//        }

        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backCallback)

        return binding.root
    }

    private fun login(rollno: String, password: String, userType: String) {
        binding.buttonSignin.isClickable = false

        when (userType) {
            "Student" -> {
                if (!checkRollNoConstraints(rollno)) {
                    Toast.makeText(context, "Enter a valid NITC roll number", Toast.LENGTH_SHORT)
                        .show()
                    binding.buttonSignin.isClickable = true
                    return
                }

            }
            "" -> {
                binding.buttonSignin.isClickable = true
                Toast.makeText(context, "Select a user type", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val loginCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@LoginFragment)
        loginCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()
            val loggedIn = LoginAccess(requireContext(),this@LoginFragment,profileViewModel).login(
                rollno,
                password,
                userType
            )
            loadingDialog.cancel()
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

        binding.buttonSignin.isClickable = true
    }

}
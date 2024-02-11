package com.kumar.nitchostelmanager.wardens.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kumar.nitchostelmanager.CircleLoadingDialog
import com.kumar.nitchostelmanager.LocalStorageAccess
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentChangePasswordBinding
import com.kumar.nitchostelmanager.profile.access.ProfileAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ChangePasswordFragment : Fragment(), CircleLoadingDialog {
    lateinit var binding: FragmentChangePasswordBinding
    val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        binding.savePasswordButtonInWardenChangePasswordFragment.setOnClickListener {
            val newPassword = binding.newPasswordInWardenChangePasswordFragment.text.toString()
            if(newPassword.isEmpty()){
                binding.newPasswordInWardenChangePasswordFragment.error = "Enter password first"
                binding.newPasswordInWardenChangePasswordFragment.requestFocus()
                return@setOnClickListener
            }
            else if(newPassword.length < 8){
                binding.newPasswordInWardenChangePasswordFragment.error = "Password must be at least 8 characters long"
                binding.newPasswordInWardenChangePasswordFragment.requestFocus()
                return@setOnClickListener
            }

            val confirmPassword = binding.confirmPasswordInWardenChangePasswordFragment.text.toString()
            if(confirmPassword.isEmpty()){
                binding.confirmPasswordInWardenChangePasswordFragment.error = "Enter password first"
                binding.confirmPasswordInWardenChangePasswordFragment.requestFocus()
                return@setOnClickListener
            }

            if(newPassword != confirmPassword){
                binding.confirmPasswordInWardenChangePasswordFragment.error = "Password don't match"
                binding.confirmPasswordInWardenChangePasswordFragment.requestFocus()
                return@setOnClickListener
            }

            showDialog(newPassword)
        }

        return binding.root
    }

    private fun showDialog(newPassword: String){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Change Password")
        dialog?.setMessage("Are you sure you want to change the password?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            savePassword(newPassword)
        })
        dialog?.create()?.show()
    }

    private fun savePassword(newPassword: String) {
        val updatePasswordCoroutineScope = CoroutineScope(Dispatchers.Main)
        val loadingDialog = getLoadingDialog(requireContext(),this@ChangePasswordFragment)

        updatePasswordCoroutineScope.launch {
            loadingDialog.create()
            loadingDialog.show()

            val updated = ProfileAccess(requireContext(),profileViewModel).updatePassword(newPassword)

            loadingDialog.cancel()
            updatePasswordCoroutineScope.cancel()

            if(updated){
                Snackbar.make(binding.layoutInWardenChangePassword,"Password updated successfully", Snackbar.LENGTH_SHORT).show()
                binding.newPasswordInWardenChangePasswordFragment.setText("")
                binding.confirmPasswordInWardenChangePasswordFragment.setText("")
            }
        }
    }

}
package com.kumar.nitchostelmanager

import android.content.Context
import androidx.fragment.app.Fragment
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel

class LocalStorageAccess(
    var parentFragment: Fragment,
    var context: Context,
    var profileViewModel: ProfileViewModel
) {

    fun storeData(userType:String,loginToken:String):Boolean{
        var sharedPreferences = parentFragment.requireActivity().getSharedPreferences("storedData",Context.MODE_PRIVATE)
        if(sharedPreferences != null){
            val editor=  sharedPreferences.edit()
            if(editor != null){
                editor.putString("userType",userType)
                editor.putString("loginToken",loginToken)
                editor.apply()
                return true
            }else return false
        }else return false
    }


    fun getData():Boolean{
        var sharedPreferences = parentFragment.requireActivity().getSharedPreferences("storedData",Context.MODE_PRIVATE)
        val userType = sharedPreferences.getString("userType",null)
        val loginToken = sharedPreferences.getString("loginToken",null)
        if(userType == null || loginToken == null){
            val editor = sharedPreferences.edit()
            editor.remove("userType")
            editor.remove("loginToken")
            editor.apply()
            return false
        }
        profileViewModel.userType = userType
        profileViewModel.loginToken = loginToken
        return true
    }

}
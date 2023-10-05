package com.kumar.nitchostelmanager.authentication.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kumar.nitchostelmanager.LocalStorageAccess
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.services.AuthService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginAccess(
    var context:Context,
    var parentFragment:Fragment,
    var profileViewModel: ProfileViewModel
) {

    suspend fun login(username:String,password:String,userType:String):Boolean{
        return suspendCoroutine { continuation ->
            val loginBody = HashMap<String,String>()
            loginBody["username"] = username
            loginBody["password"] = password
            loginBody["userType"] = userType
            val authService = ServiceBuilder.buildService(AuthService::class.java)
            var loginMethod = authService.loginAdmin(loginBody)
            when(userType){
                "Student"-> loginMethod = authService.loginStudent(loginBody)
                "Admin"-> loginMethod = authService.loginAdmin(loginBody)
                "Warden"-> loginMethod = authService.loginWarden(loginBody)
            }
            loginMethod.enqueue(object: Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        val loginToken = response.headers()["auth-token"].toString()
                        if(loginToken != ""){
                            Log.d("loginToken",loginToken)
                            val stored = LocalStorageAccess(parentFragment, context, profileViewModel).storeData(userType,loginToken)
                            if(stored){
                                profileViewModel.userType = userType
                                profileViewModel.loginToken = loginToken
                                Log.d("loginToken","stored")
                                continuation.resume(true)
                            }else{
                                Toast.makeText(context,"Error in saving user data",Toast.LENGTH_SHORT).show()
                                Log.d("loginToken","error")
                                continuation.resume(false)
                            }
                        }else continuation.resume(false)
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    Log.d("login","Error : $t")
                    continuation.resume(false)
                }

            })
        }
    }



}
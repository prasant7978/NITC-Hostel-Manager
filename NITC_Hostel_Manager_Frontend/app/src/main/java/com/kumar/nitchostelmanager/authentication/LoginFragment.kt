package com.kumar.nitchostelmanager.authentication

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kumar.nitchostelmanager.R
import com.kumar.nitchostelmanager.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private lateinit var loginBinding: FragmentLoginBinding
    private var userType: String = "Student"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false)

        loginBinding.adminLoginTypeButton.setOnClickListener {
            loginBinding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            loginBinding.adminLoginTypeButton.setTextColor(Color.WHITE)
            loginBinding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.studentLoginTypeButton.setTextColor(Color.BLACK)
            loginBinding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.contractorLoginTypeButton.setTextColor(Color.BLACK)

            userType = "Admin"
        }

        loginBinding.studentLoginTypeButton.setOnClickListener {
            loginBinding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.adminLoginTypeButton.setTextColor(Color.BLACK)
            loginBinding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            loginBinding.studentLoginTypeButton.setTextColor(Color.WHITE)
            loginBinding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.contractorLoginTypeButton.setTextColor(Color.BLACK)

            userType = "Student"
        }

        loginBinding.contractorLoginTypeButton.setOnClickListener {
            loginBinding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.adminLoginTypeButton.setTextColor(Color.BLACK)
            loginBinding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.studentLoginTypeButton.setTextColor(Color.BLACK)
            loginBinding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            loginBinding.contractorLoginTypeButton.setTextColor(Color.WHITE)

            userType = "Warden"
        }

        loginBinding.buttonSignin.setOnClickListener {
            val userEmail = loginBinding.editTextLoginEmail.text.toString()
            val userPassword = loginBinding.editTextLoginPassword.text.toString()

            if(userEmail.isEmpty() || userPassword.isEmpty()){
                Toast.makeText(context, "Please enter both email and password to login.", Toast.LENGTH_SHORT).show()
            }
            else {
                signin(userEmail, userPassword, userType)
            }
        }

        loginBinding.textViewForgotPassword.setOnClickListener {
//            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
//            startActivity(intent)
        }

        return loginBinding.root
    }

    private fun signin(email: String, password: String, userType: String) {
        loginBinding.buttonSignin.isClickable = false
        loginBinding.progressBarLogin.visibility = View.VISIBLE

        when (userType) {
            "Student" -> {
                if (!checkConstraints(email)) {
                    Toast.makeText(context, "Enter a valid nitc email id", Toast.LENGTH_SHORT).show()
                    loginBinding.buttonSignin.isClickable = true
                    loginBinding.progressBarLogin.visibility = View.INVISIBLE
                }
                else {
                    val map: HashMap<String, String> = HashMap()
                    map["email"] = email
                    map["password"] = password
                    map["userType"] = userType

//                    val loginService: AuthenticationServices = ServiceBuilder.buildService(AuthenticationServices::class.java)
//                    val requestCall = loginService.loginStudent(map)
//
//                    requestCall.enqueue(object: Callback<Boolean> {
//                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
//                            if(response.code() == 400){
//                                Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show()
//                                loginBinding.buttonSignin.isClickable = true
//                                loginBinding.progressBarLogin.visibility = View.INVISIBLE
//                            }
//                            else if(response.code() == 200){
//                                val sharedPreferences = this@LoginActivity.getSharedPreferences("saveToken", Context.MODE_PRIVATE)
//                                val editor = sharedPreferences.edit()
//                                editor.putString("token",response.headers()["user-auth-token"].toString())
//                                editor.putString("userType", "Student")
//                                editor.apply()
//
//                                val intent = Intent(this@LoginActivity, StudentDashboardActivity::class.java)
//                                intent.putExtra("userType", userType)
//                                startActivity(intent)
//
//                                loginBinding.buttonSignin.isClickable = true
//                                loginBinding.progressBarLogin.visibility = View.INVISIBLE
//
//                                finish()
//                            }
//                        }
//
//                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                            Toast.makeText(context, t.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
//                            loginBinding.buttonSignin.isClickable = true
//                            loginBinding.progressBarLogin.visibility = View.INVISIBLE
//                        }
//
//                    })
                }
            }

//            "Admin" -> {
//                val map: HashMap<String, String> = HashMap()
//                map["adminEmail"] = email
//                map["adminPassword"] = pass
//                map["userType"] = userType
//
//                val loginService: AuthenticationServices = ServiceBuilder.buildService(
//                    AuthenticationServices::class.java)
//                val requestCall = loginService.loginAdmin(map)
//
//                requestCall.enqueue(object: Callback<Boolean> {
//                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
//                        if(response.isSuccessful){
//                            val sharedPreferences = this@LoginActivity.getSharedPreferences("saveToken", Context.MODE_PRIVATE)
//                            val editor = sharedPreferences.edit()
//                            editor.putString("token", response.headers()["user-auth-token"].toString())
//                            editor.putString("userType", "Admin")
//                            editor.apply()
//
//                            val intent = Intent(this@LoginActivity, AdminDashboardActivity::class.java)
//                            startActivity(intent)
//
//                            loginBinding.buttonSignin.isClickable = true
//                            loginBinding.progressBarLogin.visibility = View.INVISIBLE
//
//                            finish()
//                        }
//                        else{
//                            Toast.makeText(applicationContext, "Incorrect username or password", Toast.LENGTH_SHORT).show()
//                            loginBinding.buttonSignin.isClickable = true
//                            loginBinding.progressBarLogin.visibility = View.INVISIBLE
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                        Toast.makeText(applicationContext, t.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
//                        loginBinding.buttonSignin.isClickable = true
//                        loginBinding.progressBarLogin.visibility = View.INVISIBLE
//                    }
//
//                })
//            }

//            "Contractor" -> {
//                val map: HashMap<String, String> = HashMap()
//                map["contractorEmail"] = email
//                map["contractorPassword"] = pass
//                map["userType"] = userType
//
//                val loginService: AuthenticationServices = ServiceBuilder.buildService(
//                    AuthenticationServices::class.java)
//                val requestCall = loginService.loginContractor(map)
//
//                requestCall.enqueue(object: Callback<Boolean> {
//                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
//                        if(response.isSuccessful){
//                            val sharedPreferences = this@LoginActivity.getSharedPreferences("saveToken", Context.MODE_PRIVATE)
//                            val editor = sharedPreferences.edit()
//                            editor.putString("token", response.headers()["user-auth-token"].toString())
//                            editor.putString("userType", "Contractor")
//                            editor.apply()
//
//                            val intent = Intent(this@LoginActivity, ContractorDashboard::class.java)
//                            intent.putExtra("userType",userType)
//                            startActivity(intent)
//
//                            loginBinding.buttonSignin.isClickable = true
//                            loginBinding.progressBarLogin.visibility = View.INVISIBLE
//
//                            finish()
//                        }
//                        else{
//                            Toast.makeText(applicationContext, "Incorrect username or password", Toast.LENGTH_SHORT).show()
//                            loginBinding.buttonSignin.isClickable = true
//                            loginBinding.progressBarLogin.visibility = View.INVISIBLE
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                        Toast.makeText(applicationContext, t.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
//                        loginBinding.buttonSignin.isClickable = true
//                        loginBinding.progressBarLogin.visibility = View.INVISIBLE
//                    }
//
//                })
//            }

            "" -> {
                loginBinding.buttonSignin.isClickable = true
                loginBinding.progressBarLogin.visibility = View.INVISIBLE
                Toast.makeText(context, "Select a user type", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkConstraints(email: String): Boolean {
        if(email.contains('_')) {
            val roll = email.substring(email.indexOf("_") + 1, email.length)
            if (roll[0] == 'm' || roll[0] == 'b' || roll[0] == 'p') {
                if(roll.contains('@')) {
                    val domain = roll.substring(roll.indexOf("@") + 1, roll.length)
                    return domain == "nitc.ac.in"
                }
                else{
                    return false
                }
            } else {
                return false
            }
        }
        else{
            return false
        }
    }
}
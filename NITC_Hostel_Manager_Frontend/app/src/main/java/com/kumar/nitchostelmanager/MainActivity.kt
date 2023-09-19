package com.kumar.nitchostelmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kumar.nitchostelmanager.authentication.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        val adminDashboardFragment = LoginFragment()

        fragmentTransaction.add(R.id.fragmentContainerView,adminDashboardFragment)
        fragmentTransaction.commit()
    }
}
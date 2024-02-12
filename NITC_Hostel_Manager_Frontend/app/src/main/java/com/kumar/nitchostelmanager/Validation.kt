package com.kumar.nitchostelmanager

import androidx.core.text.isDigitsOnly

interface Validation {
    fun checkRollNoConstraints(roll: String): Boolean {
        if (roll[0] == 'm' || roll[0] == 'b' || roll[0] == 'p') {
            return roll.length == 9
        } else {
            return false
        }
    }

    fun checkValidNITCEmail(email: String): Boolean{
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

    fun checkValidName(str: String): Boolean{
            val regex = Regex("^[a-zA-Z ]*$")
            return regex.matches(str)
    }

    fun checkValidPhoneNumber(phone: String): Boolean{
//        return !(phone[0] == '0' || phone.length != 10)
        val regex = Regex("^[789]\\d{9}$")
        return regex.matches(phone)
    }

    fun checkValidAmount(amount: String): Boolean{
        return amount.toInt() > 0 && amount.isDigitsOnly()
    }

    fun checkValidEmail(email: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//        val emailRegex = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$""")
//        return emailRegex.matches(email)
    }
}
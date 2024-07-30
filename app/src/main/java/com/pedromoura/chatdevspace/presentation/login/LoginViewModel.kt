package com.pedromoura.chatdevspace.presentation.login

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context) : ViewModel() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    var userId: String = ""
    var username: String = ""
    var password: String = ""

    fun saveCredentials(username: String, password: String) {
        userId = if (username == "userone") {
            "1"
        } else {
            "2"
        }

        viewModelScope.launch {
            with(sharedPreferences.edit()) {
                putString("USERID", userId)
                putString("USERNAME", username)
                putString("PASSWORD", password)
                apply()
            }
        }
    }

    fun validateCredentials(username: String, password: String) : Boolean {
        var flag: Boolean = false
        viewModelScope.launch {
            flag = (username == sharedPreferences.getString("USERNAME", username)) &&
                    (password == sharedPreferences.getString("PASSWORD", password))
        }

        return flag
    }

    fun loadCredentials() {
        viewModelScope.launch {
            userId = sharedPreferences.getString("USERID", "") ?: ""
            username = sharedPreferences.getString("USERNAME", "") ?: ""
            password = sharedPreferences.getString("PASSWORD", "") ?: ""
        }
    }

    fun clearCredentials() {
        viewModelScope.launch {
            with(sharedPreferences.edit()) {
                clear()
                apply()
            }
        }
    }
}
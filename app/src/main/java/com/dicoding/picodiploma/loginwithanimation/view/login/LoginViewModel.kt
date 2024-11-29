package com.dicoding.picodiploma.loginwithanimation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.LoginRequest
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun login(request: LoginRequest): LiveData<Result<UserModel>> {
        val result = MutableLiveData<Result<UserModel>>()
        viewModelScope.launch {
            try {
                val userModel = repository.login(request.email, request.password)
                result.postValue(Result.success(userModel))
            } catch (e: Exception) {
                result.postValue(Result.failure(e))
            }
        }
        return result
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}

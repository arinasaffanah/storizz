package com.dicoding.picodiploma.loginwithanimation.view.mainmenu.ui.AddStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddStoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is add story Fragment"
    }
    val text: LiveData<String> = _text
}
package com.dicoding.picodiploma.loginwithanimation.view.mainmenu.ui.Story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is story Fragment"
    }
    val text: LiveData<String> = _text
}
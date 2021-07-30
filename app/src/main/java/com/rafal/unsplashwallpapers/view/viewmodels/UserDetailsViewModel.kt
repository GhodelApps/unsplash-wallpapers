package com.rafal.unsplashwallpapers.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import com.rafal.unsplashwallpapers.model.UnsplashUser
import com.rafal.unsplashwallpapers.repository.UserDetailsRepository
import com.rafal.unsplashwallpapers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repo: UserDetailsRepository
) : ViewModel() {

    private val _userLiveData: MutableLiveData<Resource<UnsplashUser>> = MutableLiveData()
    val userLiveData: LiveData<Resource<UnsplashUser>> = _userLiveData

    private val _userPhotosLiveData: MutableLiveData<PagingData<UnsplashSearchPhoto>> =
        MutableLiveData()
    val userPhotosLiveData: LiveData<PagingData<UnsplashSearchPhoto>> = _userPhotosLiveData

    fun getUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getUser(username)
            withContext(Dispatchers.Main) {
                _userLiveData.value = result
            }
        }
    }

    fun getUserPhotos(username: String) {
        viewModelScope.launch {
            repo.getUserPhotos(username).cachedIn(viewModelScope).collect {
                _userPhotosLiveData.value = it
            }
        }
    }
}
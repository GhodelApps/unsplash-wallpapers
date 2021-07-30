package com.rafal.unsplashwallpapers.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafal.unsplashwallpapers.model.UnsplashPhoto
import com.rafal.unsplashwallpapers.repository.PhotoDetailsRepository
import com.rafal.unsplashwallpapers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val repository: PhotoDetailsRepository
) : ViewModel() {
    private val _photoLiveData: MutableLiveData<Resource<UnsplashPhoto>> = MutableLiveData()
    val photoLiveData: LiveData<Resource<UnsplashPhoto>> = _photoLiveData

    fun getPhoto(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val photo = repository.getPhoto(id)
            withContext(Dispatchers.Main) {
                _photoLiveData.value = photo
            }
        }
    }
}
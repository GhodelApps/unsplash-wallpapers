package com.rafal.unsplashwallpapers.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafal.unsplashwallpapers.model.UnsplashCollectionsResults
import com.rafal.unsplashwallpapers.model.UnsplashPhotoResults
import com.rafal.unsplashwallpapers.model.UnsplashUserResults
import com.rafal.unsplashwallpapers.repository.SearchRepository
import com.rafal.unsplashwallpapers.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _photoLiveData: MutableLiveData<UnsplashPhotoResults> = MutableLiveData()
    private val photoLiveData: LiveData<UnsplashPhotoResults> = _photoLiveData

    private val _userLiveData: MutableLiveData<UnsplashUserResults> = MutableLiveData()
    private val userLiveData: LiveData<UnsplashUserResults> = _userLiveData

    private val _collectionsLiveData: MutableLiveData<UnsplashCollectionsResults> = MutableLiveData()
    private val collectionsLiveData: LiveData<UnsplashCollectionsResults> = _collectionsLiveData

    fun getPhotoLiveData() = photoLiveData
    fun getUserLiveData() = userLiveData
    fun getCollectionsLiveData() = collectionsLiveData

    fun searchPhotos(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val searchResults = searchRepository.searchPhotos(query)) {
                is Resource.Success -> Log.d("API", "${searchResults.data!!.results}")
                is Resource.Fail -> Log.d("API", "${searchResults.message}")
            }
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val searchResults = searchRepository.searchUsers(query)) {
                is Resource.Success -> Log.d("API", "${searchResults.data!!.results}")
                is Resource.Fail -> Log.d("API", "${searchResults.message}")
            }
        }
    }

    fun searchCollections(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val searchResults = searchRepository.searchCollections(query)) {
                is Resource.Success -> Log.d("API", "${searchResults.data!!.results}")
                is Resource.Fail -> Log.d("API", "${searchResults.message}")
            }
        }
    }
}
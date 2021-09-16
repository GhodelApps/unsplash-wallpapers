package com.rafal.unsplashwallpapers.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafal.unsplashwallpapers.model.UnsplashCollection
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import com.rafal.unsplashwallpapers.model.UnsplashUser
import com.rafal.unsplashwallpapers.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _photoLiveData: MutableLiveData<PagingData<UnsplashSearchPhoto>> = MutableLiveData()
    val photoLiveData: LiveData<PagingData<UnsplashSearchPhoto>> = _photoLiveData

    private val _userLiveData: MutableLiveData<PagingData<UnsplashUser>> = MutableLiveData()
    val userLiveData: LiveData<PagingData<UnsplashUser>> = _userLiveData

    private val _collectionLiveData: MutableLiveData<PagingData<UnsplashCollection>> =
        MutableLiveData()
    val collectionLiveData: LiveData<PagingData<UnsplashCollection>> = _collectionLiveData

    fun searchPhotos(query: String, sortBy: String) {
        viewModelScope.launch {
            searchRepository.searchPhotos(query, sortBy).cachedIn(viewModelScope).collect {
                _photoLiveData.value = it
            }
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            searchRepository.searchUsers(query).cachedIn(viewModelScope).collect {
                _userLiveData.value = it
            }
        }
    }

    fun searchCollections(query: String) {
        viewModelScope.launch {
            searchRepository.searchCollections(query).cachedIn(viewModelScope).collect {
                _collectionLiveData.value = it
            }
        }
    }
}
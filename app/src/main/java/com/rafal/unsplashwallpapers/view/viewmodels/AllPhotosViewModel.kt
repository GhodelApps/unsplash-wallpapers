package com.rafal.unsplashwallpapers.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import com.rafal.unsplashwallpapers.repository.AllPhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPhotosViewModel @Inject constructor(
    private val repo: AllPhotosRepository
) : ViewModel() {
    private val _allPhotosLiveData: MutableLiveData<PagingData<UnsplashSearchPhoto>> =
        MutableLiveData()
    val allPhotosLiveData: LiveData<PagingData<UnsplashSearchPhoto>> = _allPhotosLiveData

    @ExperimentalPagingApi
    fun getAllPhotos(orderBy: String) {
        viewModelScope.launch {
            repo.getAllPhotos(orderBy).collect {
                _allPhotosLiveData.value = it
            }
        }
    }
}
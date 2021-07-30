package com.rafal.unsplashwallpapers.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import com.rafal.unsplashwallpapers.repository.CollectionPhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionPhotosViewModel @Inject constructor(
    private val repo: CollectionPhotosRepository
) : ViewModel() {
    private val _collectionPhotosLiveData: MutableLiveData<PagingData<UnsplashSearchPhoto>> =
        MutableLiveData()
    val collectionPhotosLiveData: LiveData<PagingData<UnsplashSearchPhoto>> =
        _collectionPhotosLiveData

    fun getCollectionPhotos(id: String) {
        viewModelScope.launch {
            repo.getCollectionPhotos(id).cachedIn(viewModelScope).collect {
                _collectionPhotosLiveData.value = it
            }
        }
    }
}
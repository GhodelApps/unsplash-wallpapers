package com.rafal.unsplashwallpapers.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rafal.unsplashwallpapers.databinding.FragmentPhotosBinding
import com.rafal.unsplashwallpapers.view.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: SearchViewModel by viewModels()

        viewModel.getPhotoLiveData().observe(viewLifecycleOwner) { result ->
            Log.d("API", "Live data: ${result.results}")
        }

        viewModel.searchPhotos("mouse")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
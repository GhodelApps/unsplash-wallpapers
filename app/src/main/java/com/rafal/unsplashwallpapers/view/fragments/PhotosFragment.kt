package com.rafal.unsplashwallpapers.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.rafal.unsplashwallpapers.databinding.FragmentPhotosBinding
import com.rafal.unsplashwallpapers.view.adapters.PhotosAdapter
import com.rafal.unsplashwallpapers.view.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        val pagingAdapter = PhotosAdapter()
        val recyclerView = binding.photosRv
        recyclerView.adapter = pagingAdapter

        viewModel.searchPhotosPaging("car").observe(viewLifecycleOwner) {
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle,  it)
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            delay(5000)
            viewModel.searchPhotosPaging("ball")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
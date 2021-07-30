package com.rafal.unsplashwallpapers.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rafal.unsplashwallpapers.databinding.FragmentCollectionDetailsBinding
import com.rafal.unsplashwallpapers.view.adapters.PhotosPagingAdapter
import com.rafal.unsplashwallpapers.view.adapters.ResultsLoadStateAdapter
import com.rafal.unsplashwallpapers.view.viewmodels.CollectionPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionDetailsFragment : Fragment(), PhotosPagingAdapter.onPhotoClickListener {
    private var _binding: FragmentCollectionDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CollectionPhotosViewModel by viewModels()

    private val args: CollectionDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCollectionPhotos(args.collectionID)

        val adapter = PhotosPagingAdapter(this)
        val recycler = binding.rv
        recycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ResultsLoadStateAdapter { adapter.retry() },
            footer = ResultsLoadStateAdapter { adapter.retry() }
        )

        viewModel.collectionPhotosLiveData.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.fab.setOnClickListener {
            recycler.scrollToPosition(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPhotoClick(photoID: String) {
        val action =
            CollectionDetailsFragmentDirections.actionCollectionDetailsFragmentToPhotoDetailsFragment(
                photoID = photoID
            )
        findNavController().navigate(action)
    }
}
package com.rafal.unsplashwallpapers.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rafal.unsplashwallpapers.R
import com.rafal.unsplashwallpapers.databinding.FragmentUserDetailsBinding
import com.rafal.unsplashwallpapers.util.Resource
import com.rafal.unsplashwallpapers.view.adapters.PhotosPagingAdapter
import com.rafal.unsplashwallpapers.view.adapters.ResultsLoadStateAdapter
import com.rafal.unsplashwallpapers.view.viewmodels.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment(), PhotosPagingAdapter.onPhotoClickListener {
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: UserDetailsFragmentArgs by navArgs()

    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUserData()

        val pagingAdapter = PhotosPagingAdapter(this)
        val recyclerView = binding.rv
        recyclerView.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = ResultsLoadStateAdapter { pagingAdapter.retry() },
            footer = ResultsLoadStateAdapter { pagingAdapter.retry() }
        )

        viewModel.userPhotosLiveData.observe(viewLifecycleOwner) {
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { user ->
                        binding.apply {
                            pb.visibility = View.GONE
                            successLayout.visibility = View.VISIBLE
                            userTv.text = user.name
                            downloadsCountTv.text = user.downloads.toString()
                            likesCountTv.text = user.total_likes.toString()
                            photosCountTv.text = user.total_photos.toString()
                            loadUserImage(user.profile_image.large)
                        }
                    }
                }
                is Resource.Fail -> {
                    binding.apply {
                        pb.visibility = View.GONE
                        failLayout.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.retryBtn.setOnClickListener {
            loadUserData()
            binding.apply {
                failLayout.visibility = View.GONE
                pb.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadUserImage(url: String) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .error(R.drawable.ic_baseline_error_24)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.userIv)
    }

    private fun loadUserData() {
        viewModel.getUser(args.userID)
        viewModel.getUserPhotos(args.userID)
    }

    override fun onPhotoClick(photoID: String) {
        val action =
            UserDetailsFragmentDirections.actionUserDetailsFragmentToPhotoDetailsFragment(photoID = photoID)
        findNavController().navigate(action)
    }
}
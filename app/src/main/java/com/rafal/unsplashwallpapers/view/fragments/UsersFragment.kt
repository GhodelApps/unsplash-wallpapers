package com.rafal.unsplashwallpapers.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rafal.unsplashwallpapers.databinding.FragmentUsersBinding
import com.rafal.unsplashwallpapers.view.adapters.ResultsLoadStateAdapter
import com.rafal.unsplashwallpapers.view.adapters.UsersPagingAdapter
import com.rafal.unsplashwallpapers.view.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment(), UsersPagingAdapter.onUserClickListener {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = UsersPagingAdapter(this)
        val recyclerView = binding.usersRv
        recyclerView.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = ResultsLoadStateAdapter { pagingAdapter.retry() },
            footer = ResultsLoadStateAdapter { pagingAdapter.retry() }
        )

        binding.fabUsers.setOnClickListener {
            recyclerView.scrollToPosition(0)
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.usersEmptyIv.visibility = View.GONE
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onUserClick(userID: String) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToUserDetailsFragment(userID = userID)
        findNavController().navigate(action)
    }
}
package com.rafal.unsplashwallpapers.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rafal.unsplashwallpapers.databinding.FragmentCollectionsBinding
import com.rafal.unsplashwallpapers.repository.SearchRepository
import com.rafal.unsplashwallpapers.view.adapters.CollectionsPagingAdapter
import com.rafal.unsplashwallpapers.view.adapters.ResultsLoadStateAdapter
import com.rafal.unsplashwallpapers.view.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CollectionsFragment : Fragment(), CollectionsPagingAdapter.onCollectionClickListener {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchRepository: SearchRepository

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = CollectionsPagingAdapter(this)
        val recyclerView = binding.collectionsRv
        recyclerView.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = ResultsLoadStateAdapter { pagingAdapter.retry() },
            footer = ResultsLoadStateAdapter { pagingAdapter.retry() }
        )

        binding.fab.setOnClickListener {
            recyclerView.scrollToPosition(0)
        }

        viewModel.collectionLiveData.observe(viewLifecycleOwner) {
            binding.photosEmptyIv.visibility = View.GONE
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCollectionClick(id: String, title: String) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToCollectionDetailsFragment(collectionID = id, collectionTitle = title)
        findNavController().navigate(action)
    }

}
package com.rafal.unsplashwallpapers.view.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.rafal.unsplashwallpapers.R
import com.rafal.unsplashwallpapers.databinding.FragmentAllPhotosBinding
import com.rafal.unsplashwallpapers.view.adapters.PhotosPagingAdapter
import com.rafal.unsplashwallpapers.view.adapters.ResultsLoadStateAdapter
import com.rafal.unsplashwallpapers.view.viewmodels.AllPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllPhotosFragment : Fragment(), PhotosPagingAdapter.onPhotoClickListener {

    private var _binding: FragmentAllPhotosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllPhotosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = PhotosPagingAdapter(this)
        val recycler = binding.allPhotosRv

        pagingAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                binding.apply {
                    allPhotoFailLayout.visibility = View.VISIBLE
                    allPhotosRv.visibility = View.GONE
                }
            } else {
                binding.apply {
                    allPhotoFailLayout.visibility = View.GONE
                    allPhotosRv.visibility = View.VISIBLE
                }
            }
        }

        binding.allPhotoRetryBtn.setOnClickListener {
            pagingAdapter.retry()
        }

        recycler.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = ResultsLoadStateAdapter { pagingAdapter.retry() },
            footer = ResultsLoadStateAdapter { pagingAdapter.retry() }
        )

        getAllPhotos(SORT_BY_LATEST)

        viewModel.allPhotosLiveData.observe(viewLifecycleOwner) {
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.allPhotosFab.setOnClickListener {
            recycler.scrollToPosition(0)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.start_menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    val action =
                        AllPhotosFragmentDirections.actionStartFragmentToSearchFragment(queryString = query)
                    findNavController().navigate(action)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    @ExperimentalPagingApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                PopupMenu(
                    requireActivity(),
                    requireActivity().findViewById(R.id.action_search)
                ).apply {
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.latest -> {
                                getAllPhotos(SORT_BY_LATEST)
                                true
                            }
                            R.id.oldest -> {
                                getAllPhotos(SORT_BY_OLDEST)
                                true
                            }
                            R.id.popular -> {
                                getAllPhotos(SORT_BY_POPULAR)
                                true
                            }
                            else -> false
                        }
                    }
                    inflate(R.menu.popup_menu_sort_all_photos)
                    show()
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPhotoClick(photoID: String) {
        val action =
            AllPhotosFragmentDirections.actionStartFragmentToPhotoDetailsFragment(photoID = photoID)
        findNavController().navigate(action)
    }

    @ExperimentalPagingApi
    private fun getAllPhotos(orderBy: String) {
        viewModel.getAllPhotos(orderBy)
    }

    companion object {
        const val SORT_BY_LATEST = "latest"
        const val SORT_BY_OLDEST = "oldest"
        const val SORT_BY_POPULAR = "popular"
    }


}
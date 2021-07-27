package com.rafal.unsplashwallpapers.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.rafal.unsplashwallpapers.R
import com.rafal.unsplashwallpapers.databinding.FragmentSearchBinding
import com.rafal.unsplashwallpapers.view.adapters.SearchFragmentAdapter
import com.rafal.unsplashwallpapers.view.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    private lateinit var latestSearchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = SearchFragmentAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getStringArray(R.array.main_tabs_titles)[position]
        }.attach()

        setSearchViewOnQueryTextListener()
    }

    private fun setSearchViewOnQueryTextListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    latestSearchQuery = query
                    viewModel.searchPhotos(query, SORT_BY_RELEVANT)
                    viewModel.searchUsers(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_sort -> {
                showSortPopUp(requireActivity().findViewById(R.id.action_sort))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSortPopUp(v: View) {
        val popup = PopupMenu(activity, v).apply {
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.popup_sort_relevance -> {
                        viewModel.searchPhotos(latestSearchQuery, SORT_BY_RELEVANT)
                        true
                    }
                    R.id.popup_sort_latest -> {
                        viewModel.searchPhotos(latestSearchQuery, SORT_BY_LATEST)
                        true
                    }
                    else -> false
                }
            }
            inflate(R.menu.popup_menu_sort)
            show()
        }
    }

    companion object {
        const val SORT_BY_RELEVANT = "relevant"
        const val SORT_BY_LATEST = "latest"
    }
}
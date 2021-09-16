package com.rafal.unsplashwallpapers.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rafal.unsplashwallpapers.view.fragments.CollectionsFragment
import com.rafal.unsplashwallpapers.view.fragments.PhotosFragment
import com.rafal.unsplashwallpapers.view.fragments.UsersFragment

class SearchFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return CollectionsFragment()
            2 -> return UsersFragment()
        }
        return PhotosFragment()
    }
}
package com.rafal.unsplashwallpapers.view.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.rafal.unsplashwallpapers.view.viewholders.ResultsLoadStateViewHolder

class ResultsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ResultsLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: ResultsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ResultsLoadStateViewHolder {
        return ResultsLoadStateViewHolder.create(parent, retry)
    }
}
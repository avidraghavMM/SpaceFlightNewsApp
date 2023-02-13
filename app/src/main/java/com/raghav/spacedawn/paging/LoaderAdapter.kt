package com.raghav.spacedawn.paging

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.raghav.spacedawn.ui.LoadStateViewHolder

/**
 * This adapter class is concatenated with the RecylerView's adapter which
 * shows list of items in on the UI to show loading and error states when the RecyclerView
 * is scrolled
 */
class LoaderAdapter(private val retryListener: () -> Unit) :
    LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retryListener)
    }
}

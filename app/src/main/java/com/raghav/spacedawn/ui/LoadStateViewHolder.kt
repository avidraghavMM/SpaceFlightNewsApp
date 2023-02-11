package com.raghav.spacedawn.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.raghav.spacedawn.R
import com.raghav.spacedawn.databinding.ItemLoadStateBinding

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    retryListener: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retryListener.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvErrorMessage.text = loadState.error.localizedMessage
        }
        binding.paginationProgressBar.isVisible = loadState is LoadState.Loading
        binding.btnRetry.isVisible = loadState is LoadState.Error
        binding.tvErrorMessage.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retryListener: () -> Unit): LoadStateViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_load_state, parent, false)
            val binding = ItemLoadStateBinding.bind(view)
            return LoadStateViewHolder(binding, retryListener)
        }
    }
}

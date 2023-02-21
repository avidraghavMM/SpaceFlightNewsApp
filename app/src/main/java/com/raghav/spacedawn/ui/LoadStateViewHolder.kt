package com.raghav.spacedawn.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.raghav.spacedawn.R
import com.raghav.spacedawn.databinding.ItemLoadStateBinding
import com.raghav.spacedawn.ui.common.ItemLoadState

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    private val retryListener: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.composeView.setContent {
            ItemLoadState(
                message = if (loadState is LoadState.Error) {
                    loadState.error.localizedMessage
                } else {
                    ""
                },
                isCtaVisible = loadState is LoadState.Error,
                isProgressBarVisible = loadState is LoadState.Loading
            ) {
                retryListener()
            }
        }
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

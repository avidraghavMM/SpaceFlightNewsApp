package com.raghav.spacedawn.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raghav.spacedawn.databinding.ItemLoadStateBinding

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(state: LoadState) {
            when (state) {
                LoadState.Loading -> {
                    binding.paginationProgressBar.visibility = View.VISIBLE
                }
                is LoadState.Error -> {
                    binding.itemErrorMessage.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = state.error.localizedMessage
                }
                else -> {}
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val itemBinding =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }
}
package com.raghav.spacedawn.paging

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raghav.spacedawn.databinding.ItemLoadStateBinding

class LoaderAdapter(private val retryListener: () -> Unit = {}) :
    LoadStateAdapter<LoaderAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(state: LoadState) {
            when (state) {
                LoadState.Loading -> {
                    binding.itemErrorMessage.visibility = GONE
                    binding.paginationProgressBar.visibility = VISIBLE
                }
                is LoadState.Error -> {
                    binding.paginationProgressBar.visibility = GONE
                    binding.itemErrorMessage.visibility = VISIBLE
                    binding.tvErrorMessage.text = state.error.localizedMessage
                }
                else -> {}
            }

            binding.btnRetry.setOnClickListener {
                retryListener()
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

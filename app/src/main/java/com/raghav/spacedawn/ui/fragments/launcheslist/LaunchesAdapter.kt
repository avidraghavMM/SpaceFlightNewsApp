package com.raghav.spacedawn.ui.fragments.launcheslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.accompanist.themeadapter.material.MdcTheme
import com.raghav.spacedawn.databinding.ItemLaunchPreviewBinding
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem

class LaunchesAdapter : PagingDataAdapter<LaunchLibraryResponseItem, LaunchesAdapter.ViewHolder>(
    differCallBack
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemLaunchPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch: LaunchLibraryResponseItem? = getItem(position)
        if (launch != null) {
            holder.bind(launch)
        }
    }

    class ViewHolder(private val binding: ItemLaunchPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            var onItemClickListener: ((LaunchLibraryResponseItem) -> Unit)? = null
        }

        fun bind(launch: LaunchLibraryResponseItem) {
            binding.apply {
                composeView.setContent {
                    MdcTheme {
                        ItemLaunch(launch = launch) {
                            onItemClickListener?.let { it(launch) }
                        }
                    }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (LaunchLibraryResponseItem) -> Unit) {
        ViewHolder.onItemClickListener = listener
    }

    companion object {
        private val differCallBack = object : DiffUtil.ItemCallback<LaunchLibraryResponseItem>() {
            override fun areItemsTheSame(
                oldItem: LaunchLibraryResponseItem,
                newItem: LaunchLibraryResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: LaunchLibraryResponseItem,
                newItem: LaunchLibraryResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

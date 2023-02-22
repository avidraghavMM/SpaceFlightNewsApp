package com.raghav.spacedawn.ui.fragments.articleslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.accompanist.themeadapter.material.MdcTheme
import com.raghav.spacedawn.databinding.ItemArticlePreviewBinding
import com.raghav.spacedawn.models.spaceflightapi.ArticlesResponseItem

// This adapter class is also used for RecylerView in SearchArticleFragment.kt
class ArticlesAdapter : PagingDataAdapter<ArticlesResponseItem, ArticlesAdapter.ViewHolder>(
    differCallBack
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: ArticlesResponseItem? = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    class ViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            var onItemClickListener: ((ArticlesResponseItem) -> Unit)? = null
        }

        fun bind(article: ArticlesResponseItem) {
            binding.apply {
                composeView.setContent {
                    MdcTheme {
                        ItemArticle(
                            article = article
                        )
                    }
                }
                itemView.setOnClickListener {
                    onItemClickListener?.let { it(article) }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (ArticlesResponseItem) -> Unit) {
        ViewHolder.onItemClickListener = listener
    }

    companion object {
        private val differCallBack = object : DiffUtil.ItemCallback<ArticlesResponseItem>() {
            override fun areItemsTheSame(
                oldItem: ArticlesResponseItem,
                newItem: ArticlesResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ArticlesResponseItem,
                newItem: ArticlesResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

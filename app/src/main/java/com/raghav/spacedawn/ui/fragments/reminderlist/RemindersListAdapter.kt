package com.raghav.spacedawn.ui.fragments.reminderlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raghav.spacedawn.databinding.ItemReminderPreviewBinding
import com.raghav.spacedawn.models.reminder.ReminderModelClass

class RemindersListAdapter() : RecyclerView.Adapter<RemindersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemReminderPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder: ReminderModelClass = differ.currentList[position]
        holder.bind(reminder)
    }

    override fun getItemCount() = differ.currentList.size

    class ViewHolder(private val binding: ItemReminderPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            var onItemClickListener: ((ReminderModelClass) -> Unit)? = null
        }

        fun bind(reminder: ReminderModelClass) {
            binding.composeView.setContent {
                ItemReminder(
                    imageUrl = reminder.image,
                    title = reminder.name,
                    launchDate = reminder.dateTime
                ) {
                    onItemClickListener?.let { it(reminder) }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (ReminderModelClass) -> Unit) {
        ViewHolder.onItemClickListener = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ReminderModelClass>() {
        override fun areItemsTheSame(
            oldItem: ReminderModelClass,
            newItem: ReminderModelClass
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ReminderModelClass,
            newItem: ReminderModelClass
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
}

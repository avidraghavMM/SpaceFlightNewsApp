package com.example.spaceflightnewsapp.adapters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spaceflightnewsapp.R
import com.example.spaceflightnewsapp.databinding.ItemLaunchPreviewBinding
import com.example.spaceflightnewsapp.models.launchlibrary.LaunchLibraryResponseItem
import com.example.spaceflightnewsapp.utils.AlarmBroadCastReciever
import com.example.spaceflightnewsapp.utils.Constants.Companion.DATE_OUTPUT_FORMAT
import com.example.spaceflightnewsapp.utils.Constants.Companion.LAUNCH_DATE_INPUT_FORMAT
import com.example.spaceflightnewsapp.utils.Helpers.Companion.formatTo
import com.example.spaceflightnewsapp.utils.Helpers.Companion.toDate
import java.text.SimpleDateFormat
import java.util.*


class LaunchesAdapter()  : RecyclerView.Adapter<LaunchesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemLaunchPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch: LaunchLibraryResponseItem = differ.currentList[position]
        holder.bind(launch)
    }

    override fun getItemCount() = differ.currentList.size

    class ViewHolder(private val binding: ItemLaunchPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object{ var onItemClickListener : ((LaunchLibraryResponseItem) -> Unit)? = null}


        fun bind(launch: LaunchLibraryResponseItem) {
            binding.apply {
                Glide.with(root)
                    .load(launch.image)
                    .placeholder(R.drawable.icon)
                    .error(R.drawable.icon)
                    .circleCrop()
                    .into(ivLaunchImage)

                tvAgency.text = launch.launch_service_provider.name
                tvTitle.text = launch.name
                tvRocketName.text = launch.rocket.configuration.full_name
                binding.tvStatus.setTextColor(
                    when(launch.status.name){
                        "To Be Determined" -> Color.RED
                        "Go for Launch" -> Color.GREEN
                        "To Be Confirmed" -> Color.YELLOW
                        else -> Color.WHITE
                    }
                )

                tvStatus.text = launch.status.name
                val dateTime= launch.net.toDate(LAUNCH_DATE_INPUT_FORMAT)

                tvLaunchDate.text =launch.net
                    .toDate(LAUNCH_DATE_INPUT_FORMAT)
                    .formatTo(
                    DATE_OUTPUT_FORMAT)

                btnSetAlarm.setOnClickListener {
//                    val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS")
//                    val calendar = Calendar.getInstance()
//                    calendar.timeInMillis = dateTime.time
//                     formatter.format(calendar.time)
//                    Log.e("info",(dateTime.time).toString())
                    setAlarm(dateTime.time,itemView.context)
                }

                itemView.setOnClickListener {
                    onItemClickListener?.let { it(launch) }
                }
            }
        }

        private fun setAlarm(timeInMilliseconds: Long,context: Context) {
            val am : AlarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val i = Intent(context, AlarmBroadCastReciever::class.java)
            val pi = PendingIntent.getBroadcast(context, 0, i, 0)
            am.set(AlarmManager.RTC_WAKEUP,timeInMilliseconds,pi)
            Toast.makeText(context, "Alarm is set", Toast.LENGTH_SHORT).show()
        }


    }


    fun setOnItemClickListener(listener: (LaunchLibraryResponseItem) -> Unit) {
        ViewHolder.onItemClickListener = listener
    }
    private val differCallBack = object : DiffUtil.ItemCallback<LaunchLibraryResponseItem>(){
        override fun areItemsTheSame(
            oldItem: LaunchLibraryResponseItem, newItem: LaunchLibraryResponseItem
        ): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: LaunchLibraryResponseItem, newItem: LaunchLibraryResponseItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallBack)
}
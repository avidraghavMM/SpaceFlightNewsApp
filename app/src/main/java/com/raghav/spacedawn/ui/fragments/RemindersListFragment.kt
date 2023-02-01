package com.raghav.spacedawn.ui.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghav.spacedawn.R
import com.raghav.spacedawn.adapters.RemindersListAdapter
import com.raghav.spacedawn.databinding.FragmentRemindersListBinding
import com.raghav.spacedawn.db.ReminderModelClass
import com.raghav.spacedawn.ui.AppViewModel
import com.raghav.spacedawn.utils.AlarmBroadCastReciever
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RemindersListFragment : Fragment(R.layout.fragment_reminders_list) {
    private val viewModel by viewModels<AppViewModel>()
    lateinit var binding: FragmentRemindersListBinding
    lateinit var reminderListAdapter: RemindersListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRemindersListBinding.bind(view)
        setupRecyclerView()

        reminderListAdapter.setOnItemClickListener {
            cancelAlarm(it)
        }
        viewModel.getReminders().observe(
            viewLifecycleOwner,
            Observer {
                if (it.isNullOrEmpty()) {
                    binding.tvNoRemindersForNow.visibility = View.VISIBLE
                }
                reminderListAdapter.differ.submitList(it.reversed())
            }
        )
    }

    private fun cancelAlarm(reminder: ReminderModelClass) {
        val am: AlarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(activity, AlarmBroadCastReciever::class.java)
        val pi = PendingIntent.getBroadcast(
            activity,
            reminder.pendingIntentId,
            i,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        am.cancel(pi)
        pi.cancel()
        Toast.makeText(activity, "Reminder Cancelled", Toast.LENGTH_LONG).show()
        lifecycleScope.launch {
            viewModel.deleteReminder(reminder)
        }
    }

    private fun setupRecyclerView() {
        reminderListAdapter = RemindersListAdapter()
        binding.rvSavedReminders.apply {
            adapter = reminderListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}

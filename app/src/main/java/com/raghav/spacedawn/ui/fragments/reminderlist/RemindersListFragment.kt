package com.raghav.spacedawn.ui.fragments.reminderlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raghav.spacedawn.R
import com.raghav.spacedawn.databinding.FragmentRemindersListBinding
import com.raghav.spacedawn.ui.viewmodels.RemindersListFragmentVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemindersListFragment : Fragment(R.layout.fragment_reminders_list) {
    private val viewModel by viewModels<RemindersListFragmentVM>()
    private lateinit var binding: FragmentRemindersListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRemindersListBinding.bind(view)

//        viewModel.getReminders().observe(
//            viewLifecycleOwner
//        ) {
//            if (it.isNullOrEmpty()) {
//                binding.tvNoRemindersForNow.visibility = View.VISIBLE
//            }
//            binding.composeView.setContent {
//                RemindersList(it.reversed()) { reminder ->
//                    cancelAlarm(reminder)
//                }
//            }
//        }
    }

//    private fun cancelAlarm(reminder: ReminderModelClass) {
//        val am: AlarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val i = Intent(activity, AlarmBroadCastReciever::class.java)
//        val pi = PendingIntent.getBroadcast(
//            activity,
//            reminder.pendingIntentId,
//            i,
//            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//        am.cancel(pi)
//        pi.cancel()
//        Toast.makeText(activity, getString(R.string.reminder_cancelled), Toast.LENGTH_LONG).show()
//        lifecycleScope.launch {
//            viewModel.deleteReminder(reminder)
//        }
//    }
}

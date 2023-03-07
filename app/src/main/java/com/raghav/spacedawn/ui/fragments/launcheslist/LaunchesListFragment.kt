package com.raghav.spacedawn.ui.fragments.launcheslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raghav.spacedawn.R
import com.raghav.spacedawn.databinding.FragmentLaunchesListBinding
import com.raghav.spacedawn.ui.viewmodels.LaunchesListFragmentVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchesListFragment : Fragment(R.layout.fragment_launches_list) {

    private val viewModel by viewModels<LaunchesListFragmentVM>()

    private lateinit var binding: FragmentLaunchesListBinding

    companion object {
        private const val TAG = "LaunchesListFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLaunchesListBinding.bind(view)

        binding.composeView.setContent {
//            LaunchesListScreen(viewModel) { launch ->
//                val dateTime = launch.net.toDate(Constants.LAUNCH_DATE_INPUT_FORMAT)
//                CoroutineScope(Dispatchers.IO).launch {
//                    if (viewModel.getLaunchId(launch.id)) {
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(
//                                activity,
//                                getString(R.string.already_set),
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    } else {
//                        withContext(Dispatchers.Main) {
//                            val timeToSetAlarm: Long = dateTime.time - MinutestoMiliseconds
//                            setAlarm(timeToSetAlarm, System.currentTimeMillis().toInt(), launch)
//                        }
//                    }
//                }
//            }
        }
    }

//    private fun setAlarm(
//        timeInMilliseconds: Long,
//        pendingIntentId: Int,
//        launch: LaunchLibraryResponseItem
//    ) {
//        val nameOfLaunch = launch.name
//        val idOfLaucnh = launch.id
//        val imageUrl = launch.image
//        val dateTimeOfLaunch = launch.net.toDate(Constants.LAUNCH_DATE_INPUT_FORMAT).formatTo(
//            Constants.DATE_OUTPUT_FORMAT
//        )
//
//        val am: AlarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val i = Intent(activity, AlarmBroadCastReciever::class.java)
//        val pi = PendingIntent.getBroadcast(
//            activity,
//            pendingIntentId,
//            i,
//            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val reminder = ReminderModelClass(
//            idOfLaucnh,
//            nameOfLaunch,
//            dateTimeOfLaunch,
//            pendingIntentId,
//            STATUS_SET,
//            imageUrl
//        )
//        lifecycleScope.launch {
//            viewModel.saveReminder(reminder)
//        }
//        am.setExact(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pi)
//        Toast.makeText(
//            activity,
//            getString(R.string.reminder_set_for_launch),
//            Toast.LENGTH_LONG
//        ).show()
//    }
}

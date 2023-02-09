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
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghav.spacedawn.R
import com.raghav.spacedawn.adapters.LaunchesAdapter
import com.raghav.spacedawn.databinding.FragmentLaunchesListBinding
import com.raghav.spacedawn.models.launchlibrary.LaunchLibraryResponseItem
import com.raghav.spacedawn.models.reminder.ReminderModelClass
import com.raghav.spacedawn.paging.LoaderAdapter
import com.raghav.spacedawn.ui.viewmodels.LaunchesListFragmentVM
import com.raghav.spacedawn.utils.AlarmBroadCastReciever
import com.raghav.spacedawn.utils.Constants
import com.raghav.spacedawn.utils.Constants.Companion.MinutestoMiliseconds
import com.raghav.spacedawn.utils.Constants.Companion.STATUS_SET
import com.raghav.spacedawn.utils.Helpers.Companion.formatTo
import com.raghav.spacedawn.utils.Helpers.Companion.toDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LaunchesListFragment : Fragment(R.layout.fragment_launches_list) {

    private val viewModel by viewModels<LaunchesListFragmentVM>()
    private lateinit var launchesAdapter: LaunchesAdapter
    private lateinit var binding: FragmentLaunchesListBinding

    companion object {
        private const val TAG = "LaunchesListFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLaunchesListBinding.bind(view)
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.launchesList.collect {
                launchesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

        launchesAdapter.setOnItemClickListener {
            val dateTime = it.net.toDate(Constants.LAUNCH_DATE_INPUT_FORMAT)
            CoroutineScope(Dispatchers.IO).launch {
                if (viewModel.getLaunchId(it.id)) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Already Set", Toast.LENGTH_LONG).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        val timeToSetAlarm: Long = dateTime.time - MinutestoMiliseconds
                        setAlarm(timeToSetAlarm, System.currentTimeMillis().toInt(), it)
                    }
                }
            }
        }

        binding.btnRetry.setOnClickListener {
            launchesAdapter.refresh()
        }
    }

    private fun setAlarm(
        timeInMilliseconds: Long,
        pendingIntentId: Int,
        launch: LaunchLibraryResponseItem
    ) {
        val nameOfLaunch = launch.name
        val idOfLaucnh = launch.id
        val imageUrl = launch.image
        val dateTimeOfLaunch = launch.net.toDate(Constants.LAUNCH_DATE_INPUT_FORMAT).formatTo(
            Constants.DATE_OUTPUT_FORMAT
        )

        val am: AlarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(activity, AlarmBroadCastReciever::class.java)
        val pi = PendingIntent.getBroadcast(
            activity,
            pendingIntentId,
            i,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val reminder = ReminderModelClass(
            idOfLaucnh,
            nameOfLaunch,
            dateTimeOfLaunch,
            pendingIntentId,
            STATUS_SET,
            imageUrl
        )
        lifecycleScope.launch {
            viewModel.saveReminder(reminder)
        }
        am.setExact(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pi)
        Toast.makeText(
            activity,
            "Reminder set for 15 minutes prior to launch time",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showProgressBar(visibility: Boolean) {
        binding.paginationProgressBar.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    private fun showErrorMessage(visibility: Boolean, message: String = "") {
        if (visibility) {
            binding.itemErrorMessage.visibility = View.VISIBLE
            binding.tvErrorMessage.text = message
        } else {
            binding.itemErrorMessage.visibility = View.INVISIBLE
        }
    }

    private fun setupRecyclerView() {
        launchesAdapter = LaunchesAdapter()
        launchesAdapter.addLoadStateListener {
            showProgressBar(it.refresh is LoadState.Loading)
            showErrorMessage(
                it.refresh is LoadState.Error,
                getString(R.string.failed_to_connect)
            )
        }

        binding.rvArticles.apply {
            adapter = launchesAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter {
                    launchesAdapter.refresh()
                },
                footer = LoaderAdapter {
                    launchesAdapter.refresh()
                }
            )
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
    }
}

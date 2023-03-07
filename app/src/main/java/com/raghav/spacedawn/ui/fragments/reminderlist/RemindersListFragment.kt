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
    }
}

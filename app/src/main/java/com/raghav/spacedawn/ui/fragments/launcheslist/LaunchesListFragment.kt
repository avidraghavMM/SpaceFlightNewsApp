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
    }
}

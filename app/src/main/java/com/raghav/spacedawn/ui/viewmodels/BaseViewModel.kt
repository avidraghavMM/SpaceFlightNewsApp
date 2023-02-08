package com.raghav.spacedawn.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {
    var job: Job? = null
}
